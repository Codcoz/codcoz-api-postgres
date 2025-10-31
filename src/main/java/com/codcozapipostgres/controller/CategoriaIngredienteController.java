package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.CategoriaIngredienteRequestDTO;
import com.codcozapipostgres.dto.CategoriaIngredienteResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.CategoriaIngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria-ingrediente")
@Tag(name = "Categoria Ingrediente", description = "Métodos para gerenciar categorias de ingredientes.")
public class CategoriaIngredienteController {
    private final CategoriaIngredienteService categoriaIngredienteService;

    public CategoriaIngredienteController(CategoriaIngredienteService categoriaIngredienteService) {
        this.categoriaIngredienteService = categoriaIngredienteService;
    }

    @Operation(
            summary = "Cadastrar nova categoria de ingrediente",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                            content = @Content(schema = @Schema(implementation = CategoriaIngredienteResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Erro ao criar categoria",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                        {"erro":"Dados inválidos.","descricao":"Erro ao criar categoria.","status":400}
                    """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping("/inserir")
    public ResponseEntity<?> criarCategoriaIngrediente(@RequestBody CategoriaIngredienteRequestDTO dto) {
        try {
            CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.criarCategoriaIngrediente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao criar categoria", e.getMessage(), 400));
        }
    }

    @Operation(
            summary = "Atualizar categoria de ingrediente existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = CategoriaIngredienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                        {"erro":"Objeto não encontrado.","descricao":"Categoria informada não existe.","status":404}
                    """))),
                    @ApiResponse(responseCode = "400", description = "Erro ao atualizar categoria",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCategoriaIngrediente(@Parameter(description = "ID da categoria a ser modificada") @PathVariable Long id, @RequestBody CategoriaIngredienteRequestDTO dto) {
        try {
            CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.atualizarCategoriaIngrediente(id, dto);
            return ResponseEntity.ok(categoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Objeto não encontrado", e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao atualizar categoria", e.getMessage(), 400));
        }
    }

    @Operation(
            summary = "Deletar categoria de ingrediente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                        {"erro":"Objeto não encontrado.","descricao":"Categoria informada não existe.","status":404}
                    """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> deletarCategoriaIngrediente(@Parameter(description = "ID da categoria a ser deletada", example = "1") @PathVariable Long id) {
        try {
            categoriaIngredienteService.deletarCategoriaIngrediente(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Objeto não encontrado", e.getMessage(), 404));
        }
    }

    @Operation(
            summary = "Listar todas as categorias de ingredientes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = CategoriaIngredienteResponseDTO.class)))
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaIngredienteResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaIngredienteService.listarTodas());
    }

    @Operation(
            summary = "Buscar categoria de ingrediente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                            content = @Content(schema = @Schema(implementation = CategoriaIngredienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                        {"erro":"Objeto não encontrado.","descricao":"Categoria informada não existe.","status":404}
                    """)))
            }
    )
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(
            @Parameter(description = "ID da categoria a ser buscada", example = "1") @PathVariable Long id) {
        try {
            CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.buscarPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Objeto não encontrado", e.getMessage(), 404));
        }
    }
}