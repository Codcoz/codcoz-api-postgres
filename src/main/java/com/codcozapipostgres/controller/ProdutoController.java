package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/quantidade-estoque/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoque(@PathVariable Integer idEmpresa){
        try{
            return ResponseEntity.ok(produtoService.quantidadeProdutosEstoque(idEmpresa));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/quantidade-estoque-baixo/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoqueBaixo(@PathVariable Integer idEmpresa){
        try{
            return ResponseEntity.ok(produtoService.quantidadeProdutosEstoqueBaixo(idEmpresa));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/quantidade-proximo-validade/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeProximoValidade(@PathVariable Integer idEmpresa){
        try{
            return ResponseEntity.ok(produtoService.quantidadeProdutosProximosValidade(idEmpresa));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/listar/estoque-baixo/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoqueBaixo(@PathVariable Integer idEmpresa){
        try{
            return ResponseEntity.ok(produtoService.listarProdutosEstoqueBaixo(idEmpresa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar/proximos-validade/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProximosValidade(@PathVariable Integer idEmpresa){
        try{
            return ResponseEntity.ok(produtoService.listarProdutosProximosValidade(idEmpresa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar/estoque/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoque(@PathVariable Integer idEmpresa){
        try{
            return ResponseEntity.ok(produtoService.listarProdutosEstoque(idEmpresa));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{codigoEan}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorCodigoEan(@PathVariable Long codigoEan){
        try {
            return ResponseEntity.ok(produtoService.buscarProdutoPorCodigoEan(codigoEan));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
