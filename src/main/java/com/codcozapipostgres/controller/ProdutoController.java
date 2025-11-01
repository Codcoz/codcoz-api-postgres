package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Operações para gerenciar os produtos em estoque.")
@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Obtém a quantidade total de produtos em estoque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada ou sem produtos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no banco de dados",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/quantidade-estoque/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoque(
            @Parameter(description = "ID da empresa", example = "1") @PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.quantidadeEstoque(idEmpresa));
    }

    @Operation(summary = "Obtém a quantidade de produtos com estoque baixo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no banco de dados",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/quantidade/estoque-baixo/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoqueBaixo(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.quantidadeEstoqueBaixo(idEmpresa));
    }

    @Operation(summary = "Obtém a quantidade de produtos próximos do vencimento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto próximo do vencimento encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no banco de dados",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/quantidade/proximo-validade/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeProximoValidade(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.quantidadeProximosValidade(idEmpresa));
    }

    @Operation(summary = "Lista os produtos com estoque baixo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/listar/estoque-baixo/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoqueBaixo(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.listaEstoqueBaixo(idEmpresa));
    }

    @Operation(summary = "Lista os produtos próximos do vencimento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/listar/proximo-validade/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProximosValidade(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.listaProximosValidade(idEmpresa));
    }

    @Operation(summary = "Lista o estoque completo de produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/listar/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoque(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.listaEstoque(idEmpresa));
    }

    @Operation(summary = "Busca produto pelo código EAN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/buscar/{codigoEan}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorCodigoEan(@PathVariable String codigoEan) {
        return ResponseEntity.ok(produtoService.buscaPorCodigoEan(codigoEan));
    }

    @Operation(summary = "Registra nova entrada de produto no estoque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/entrada/{codigoEan}")
    public ResponseEntity<String> entradaProduto(
            @PathVariable String codigoEan,
            @RequestParam Integer quantidade) {
        produtoService.novaEntrada(codigoEan, quantidade);
        return ResponseEntity.ok("Entrada realizada com sucesso.");
    }

    @Operation(summary = "Registra baixa de produto no estoque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Baixa registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/baixa/{codigoEan}")
    public ResponseEntity<String> baixaProduto(
            @PathVariable String codigoEan,
            @RequestParam Integer quantidade) {
        produtoService.novaBaixa(codigoEan, quantidade);
        return ResponseEntity.ok("Baixa realizada com sucesso.");
    }
}