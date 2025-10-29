package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.IngredienteRequestDTO;
import com.codcozapipostgres.dto.IngredienteResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ingrediente", description = "Operações para gerenciar os ingredientes do sistema.")
@RestController
@RequestMapping("/ingrediente")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @Operation(summary = "Lista todos os ingredientes",
            description = "Retorna uma lista contendo todos os ingredientes cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ingredientes retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredienteResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar ingredientes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Falha ao listar os ingredientes.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @GetMapping("/listar")
    public ResponseEntity<List<IngredienteResponseDTO>> listarTodos() {
        List<IngredienteResponseDTO> ingredientes = ingredienteService.listarTodos();
        return ResponseEntity.ok(ingredientes);
    }

    @Operation(summary = "Busca um ingrediente pelo ID",
            description = "Retorna os dados do ingrediente com base no ID informado.",
            parameters = {
                    @Parameter(name = "id", description = "ID do ingrediente que deseja buscar", example = "1")
            })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Ingrediente não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Ingrediente não registrado no banco.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar ingrediente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro inesperado ao buscar ingrediente.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<IngredienteResponseDTO> buscarPorId(@PathVariable Long id) {
        IngredienteResponseDTO ingrediente = ingredienteService.buscarPorId(id);
        return ResponseEntity.ok(ingrediente);
    }

    @Operation(summary = "Cria um novo ingrediente",
            description = "Insere um novo ingrediente no banco de dados com as informações fornecidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro de validação",
                                    value = """
                                            {
                                              "erro": "Dados inválidos.",
                                              "descricao": "Campos obrigatórios não foram preenchidos corretamente.",
                                              "status": 400
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao salvar ingrediente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro ao salvar o ingrediente no banco de dados.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @PostMapping("/inserir")
    public ResponseEntity<IngredienteResponseDTO> criarIngrediente(@RequestBody IngredienteRequestDTO ingredienteRequestDTO) {
        IngredienteResponseDTO ingrediente = ingredienteService.criarIngrediente(ingredienteRequestDTO);
        return ResponseEntity.ok(ingrediente);
    }

    @Operation(summary = "Atualiza um ingrediente existente",
            description = "Atualiza as informações de um ingrediente já cadastrado no banco de dados. É necessário informar o ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID do ingrediente a ser atualizado", example = "1")
            })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Ingrediente não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Ingrediente não registrado no banco.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Dados inválidos",
                                    value = """
                                            {
                                              "erro": "Requisição inválida.",
                                              "descricao": "Nenhuma informação foi inserida para atualização.",
                                              "status": 400
                                            }
                                            """
                            )))
    })
    @PutMapping("/atualizar")
    public ResponseEntity<IngredienteResponseDTO> atualizarIngrediente(@RequestBody IngredienteRequestDTO ingredienteRequestDTO) {
        IngredienteResponseDTO ingrediente = ingredienteService.atualizarIngrediente(ingredienteRequestDTO);
        return ResponseEntity.ok(ingrediente);
    }

    @Operation(summary = "Deleta um ingrediente",
            description = "Remove permanentemente um ingrediente do banco de dados com base no ID informado.",
            parameters = {
                    @Parameter(name = "id", description = "ID do ingrediente a ser deletado", example = "1")
            })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Ingrediente não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Ingrediente não registrado no banco.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao deletar ingrediente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro inesperado ao deletar ingrediente.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarIngrediente(@PathVariable Long id) {
        ingredienteService.deletarIngrediente(id);
        return ResponseEntity.ok().build();
    }
}
