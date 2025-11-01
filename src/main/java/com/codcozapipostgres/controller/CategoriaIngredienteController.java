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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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
    public ResponseEntity<CategoriaIngredienteResponseDTO> criaCategoriaIngrediente(@RequestBody CategoriaIngredienteRequestDTO dto) {
        CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.criaCategoriaIngrediente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
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
    public ResponseEntity<CategoriaIngredienteResponseDTO> atualizaCategoriaIngrediente(@Parameter(description = "ID da categoria a ser modificada") @PathVariable Long id, @RequestBody CategoriaIngredienteRequestDTO dto) {
        CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.atualizaCategoriaIngrediente(id, dto);
        return ResponseEntity.ok(categoria);
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
    public ResponseEntity<String> deletarCategoriaIngrediente(@Parameter(description = "ID da categoria a ser deletada", example = "1") @PathVariable Long id) {
        categoriaIngredienteService.deletaCategoriaIngrediente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria deletada com sucesso.");
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
        return ResponseEntity.ok(categoriaIngredienteService.listaTodas());
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
    public ResponseEntity<CategoriaIngredienteResponseDTO> buscarCategoriaPorId(
        @Parameter(description = "ID da categoria a ser buscada", example = "1") @PathVariable Long id) {
        CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.buscaPorId(id);
        return ResponseEntity.ok(categoria);
    }
}