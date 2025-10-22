package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.ProdutoRequestDTO;
import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.service.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Produtos", description = "Operações para gerenciar os produtos em estoque.")
@Controller
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/quantidade-estoque/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoque(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.quantidadeEstoque(idEmpresa));
    }
    @GetMapping("/quantidade/estoque-baixo/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoqueBaixo(@PathVariable Integer idEmpresa){
       return ResponseEntity.ok(produtoService.quantidadeEstoqueBaixo(idEmpresa));
    }
    @GetMapping("/quantidade/proximo-validade/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeProximoValidade(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.quantidadeProximosValidade(idEmpresa));
    }

    @GetMapping("/listar/estoque-baixo/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoqueBaixo(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.listarEstoqueBaixo(idEmpresa));
    }

    @GetMapping("/listar/proximo-validade/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProximosValidade(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.listarProximosValidade(idEmpresa));
    }

    @GetMapping("/listar/estoque/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoque(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.listarEstoque(idEmpresa));
    }

    @GetMapping("/buscar/{codigoEan}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorCodigoEan(@PathVariable String codigoEan) {
        return ResponseEntity.ok(produtoService.buscarPorCodigoEan(codigoEan));
    }

    @PutMapping("/entrada/{codigoEan}")
    public ResponseEntity<String> entradaProduto(@PathVariable String codigoEan, @RequestParam Integer quantidade){
        produtoService.novaEntrada(codigoEan,quantidade);
        return ResponseEntity.ok("Entrada realizada com sucesso.");
    }
    @PutMapping("/baixa/{codigoEan}")
    public ResponseEntity<String> baixaProduto(@PathVariable String codigoEan, @RequestParam Integer quantidade){
        produtoService.novaBaixa(codigoEan,quantidade);
        return ResponseEntity.ok("Baixa realizada com sucesso.");
    }
}
