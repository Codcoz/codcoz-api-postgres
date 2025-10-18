package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return ResponseEntity.ok(produtoService.quantidadeProdutosEstoque(idEmpresa));
    }
    @GetMapping("/quantidade/estoque-baixo/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoqueBaixo(@PathVariable Integer idEmpresa){
       return ResponseEntity.ok(produtoService.quantidadeProdutosEstoqueBaixo(idEmpresa));
    }
    @GetMapping("/quantidade/proximo-validade/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeProximoValidade(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.quantidadeProdutosProximosValidade(idEmpresa));
    }

    @GetMapping("/listar/estoque-baixo/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoqueBaixo(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.listarProdutosEstoqueBaixo(idEmpresa));
    }

    @GetMapping("/listar/proximo-validade/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProximosValidade(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.listarProdutosProximosValidade(idEmpresa));
    }

    @GetMapping("/listar/estoque/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoque(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(produtoService.listarProdutosEstoque(idEmpresa));
    }

    @GetMapping("/buscar/{codigoEan}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorCodigoEan(@PathVariable Long codigoEan){
        return ResponseEntity.ok(produtoService.buscarProdutoPorCodigoEan(codigoEan));
    }
}
