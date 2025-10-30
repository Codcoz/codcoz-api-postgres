package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.ProdutoRequestDTO;
import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Operações para gerenciar os produtos em estoque.")
@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(
            summary = "Obtém a quantidade total de produtos em estoque",
            description = "Retorna o número total de produtos cadastrados para a empresa informada.",
            parameters = {
                    @Parameter(name = "idEmpresa", description = "Identificador da empresa no banco de dados", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "404", description = "Empresa não encontrada ou sem produtos cadastrados",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Empresa não encontrada ou sem produtos cadastrados.",
                                              "status": 404
                                            }
                                            """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao consultar o banco de dados",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Erro de persistência no banco de dados.",
                                              "descricao": "Erro ao acessar o banco de dados.",
                                              "status": 500
                                            }
                                            """)))
            }
    )
    @GetMapping("/quantidade-estoque/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoque(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.quantidadeEstoque(idEmpresa));
    }

    @Operation(
            summary = "Obtém a quantidade de produtos com estoque baixo",
            description = "Retorna o número de produtos abaixo do estoque mínimo da empresa informada.",
            parameters = {
                    @Parameter(name = "idEmpresa", description = "Identificador da empresa", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhum produto com estoque baixo encontrado.",
                                              "status": 404
                                            }
                                            """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao acessar o banco de dados",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Erro de persistência no banco de dados.",
                                              "descricao": "Erro ao acessar o banco de dados.",
                                              "status": 500
                                            }
                                            """)))
            }
    )
    @GetMapping("/quantidade/estoque-baixo/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeEstoqueBaixo(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.quantidadeEstoqueBaixo(idEmpresa));
    }

    @Operation(
            summary = "Obtém a quantidade de produtos próximos do vencimento",
            description = "Retorna o número de produtos cuja validade expira em até 14 dias.",
            parameters = {
                    @Parameter(name = "idEmpresa", description = "Identificador da empresa", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhum produto próximo do vencimento encontrado.",
                                              "status": 404
                                            }
                                            """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno ao acessar o banco de dados",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Erro de persistência no banco de dados.",
                                              "descricao": "Erro ao acessar o banco de dados.",
                                              "status": 500
                                            }
                                            """)))
            }
    )
    @GetMapping("/quantidade/proximo-validade/{idEmpresa}")
    public ResponseEntity<Integer> quantidadeProximoValidade(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.quantidadeProximosValidade(idEmpresa));
    }

    @Operation(
            summary = "Lista os produtos com estoque baixo",
            description = "Retorna uma lista de produtos abaixo do estoque mínimo para a empresa informada.",
            parameters = {
                    @Parameter(name = "idEmpresa", description = "Identificador da empresa", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto com estoque baixo encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhum produto com estoque baixo encontrado para esta empresa.",
                                              "status": 404
                                            }
                                            """)))
            }
    )
    @GetMapping("/listar/estoque-baixo/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoqueBaixo(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.listarEstoqueBaixo(idEmpresa));
    }

    @Operation(
            summary = "Lista os produtos próximos do vencimento",
            description = "Retorna uma lista de produtos cuja validade expira em até 14 dias.",
            parameters = {
                    @Parameter(name = "idEmpresa", description = "Identificador da empresa", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado próximo ao vencimento",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhum produto próximo da validade encontrado para esta empresa.",
                                              "status": 404
                                            }
                                            """)))
            }
    )
    @GetMapping("/listar/proximo-validade/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProximosValidade(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.listarProximosValidade(idEmpresa));
    }

    @Operation(
            summary = "Lista o estoque completo de produtos",
            description = "Retorna todos os produtos cadastrados no estoque da empresa informada.",
            parameters = {
                    @Parameter(name = "idEmpresa", description = "Identificador da empresa", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado no estoque",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhum produto encontrado no estoque para esta empresa.",
                                              "status": 404
                                            }
                                            """)))
            }
    )
    @GetMapping("/listar/estoque/{idEmpresa}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarEstoque(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(produtoService.listarEstoque(idEmpresa));
    }

    @Operation(
            summary = "Busca produto pelo código EAN",
            description = "Retorna os dados de um produto específico a partir de seu código EAN.",
            parameters = {
                    @Parameter(name = "codigoEan", description = "Código EAN do produto", example = "7891234567890")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Produto com código EAN 7891234567890 não encontrado.",
                                              "status": 404
                                            }
                                            """)))
            }
    )
    @GetMapping("/buscar/{codigoEan}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorCodigoEan(@PathVariable String codigoEan) {
        return ResponseEntity.ok(produtoService.buscarPorCodigoEan(codigoEan));
    }

    @Operation(
            summary = "Registra nova entrada de produto no estoque",
            description = "Adiciona a quantidade informada ao estoque de um produto existente, identificado pelo código EAN.",
            parameters = {
                    @Parameter(name = "codigoEan", description = "Código EAN do produto", example = "12345678"),
                    @Parameter(name = "quantidade", description = "Quantidade a ser adicionada", example = "20")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entrada registrada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                              "mensagem": "Entrada realizada com sucesso."
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Parâmetros inválidos.",
                                              "descricao": "Quantidade informada é inválida.",
                                              "status": 400
                                            }
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Produto com código EAN 12345678 não encontrado.",
                                              "status": 404
                                            }
                                            """)))
            }
    )
    @PutMapping("/entrada/{codigoEan}")
    public ResponseEntity<String> entradaProduto(@PathVariable String codigoEan, @RequestParam Integer quantidade) {
        produtoService.novaEntrada(codigoEan, quantidade);
        return ResponseEntity.ok("Entrada realizada com sucesso.");
    }

    @Operation(
            summary = "Registra baixa de produto no estoque",
            description = "Diminui a quantidade informada do estoque de um produto existente, identificado pelo código EAN.",
            parameters = {
                    @Parameter(name = "codigoEan", description = "Código EAN do produto", example = "12345678"),
                    @Parameter(name = "quantidade", description = "Quantidade a ser subtraída", example = "5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Baixa registrada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                              "mensagem": "Baixa realizada com sucesso."
                                            }
                                            """))),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Parâmetros inválidos.",
                                              "descricao": "Quantidade informada é inválida.",
                                              "status": 400
                                            }
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Produto com código EAN 12345678 não encontrado.",
                                              "status": 404
                                            }
                                            """)))
            }
    )
    @PutMapping("/baixa/{codigoEan}")
    public ResponseEntity<String> baixaProduto(@PathVariable String codigoEan, @RequestParam Integer quantidade) {
        produtoService.novaBaixa(codigoEan, quantidade);
        return ResponseEntity.ok("Baixa realizada com sucesso.");
    }
}
