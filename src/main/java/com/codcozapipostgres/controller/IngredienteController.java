package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.IngredienteRequestDTO;
import com.codcozapipostgres.dto.IngredienteResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Tag(name = "Ingrediente", description = "Operações para gerenciar os ingredientes do sistema.")
@RestController
@RequestMapping("/ingrediente")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @Operation(
            summary = "Lista todos os ingredientes",
            description = "Retorna uma lista contendo todos os ingredientes cadastrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = IngredienteResponseDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/listar")
    public ResponseEntity<List<IngredienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(ingredienteService.listaIngredientes());
    }

    @Operation(
            summary = "Busca um ingrediente pelo ID",
            description = "Retorna os dados do ingrediente com base no ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente encontrado",
            content = @Content(schema = @Schema(implementation = IngredienteResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/buscar/{id}")
    public ResponseEntity<IngredienteResponseDTO> buscarPorId(
            @Parameter(description = "ID do ingrediente", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(ingredienteService.buscaIngrediente(id));
    }

    @Operation(
            summary = "Cria um novo ingrediente",
            description = "Insere um novo ingrediente no banco de dados."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente criado com sucesso",
            content = @Content(schema = @Schema(implementation = IngredienteResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/inserir")
    public ResponseEntity<IngredienteResponseDTO> criarIngrediente(
            @RequestBody IngredienteRequestDTO ingredienteRequestDTO) {
        return ResponseEntity.ok(ingredienteService.criaIngrediente(ingredienteRequestDTO));
    }

    @Operation(
            summary = "Atualiza um ingrediente existente",
            description = "Atualiza as informações de um ingrediente cadastrado."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = IngredienteResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/atualizar")
    public ResponseEntity<IngredienteResponseDTO> atualizarIngrediente(
            @RequestBody IngredienteRequestDTO ingredienteRequestDTO) {
        return ResponseEntity.ok(ingredienteService.atualizaIngrediente(ingredienteRequestDTO));
    }

    @Operation(
            summary = "Deleta um ingrediente",
            description = "Remove permanentemente um ingrediente do banco de dados."
    )
    @ApiResponse(responseCode = "200", description = "Ingrediente deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarIngrediente(
            @Parameter(description = "ID do ingrediente", example = "1") @PathVariable Long id) {
        ingredienteService.deletaIngrediente(id);
        return ResponseEntity.ok().build();
    }
}
