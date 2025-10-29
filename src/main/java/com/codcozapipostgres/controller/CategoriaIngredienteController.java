package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.CategoriaIngredienteRequestDTO;
import com.codcozapipostgres.dto.CategoriaIngredienteResponseDTO;
import com.codcozapipostgres.service.CategoriaIngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria-ingrediente")
public class CategoriaIngredienteController {

    private final CategoriaIngredienteService categoriaIngredienteService;

    public CategoriaIngredienteController(CategoriaIngredienteService categoriaIngredienteService) {
        this.categoriaIngredienteService = categoriaIngredienteService;
    }

    @Operation(summary = "Cria uma nova categoria de ingrediente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar categoria")
    })
    @PostMapping("/inserir")
    public ResponseEntity<?> criarCategoriaIngrediente(@RequestBody CategoriaIngredienteRequestDTO dto) {
        try {
            CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.criarCategoriaIngrediente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar categoria: " + e.getMessage());
        }
    }

    @Operation(summary = "Atualiza uma categoria de ingrediente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarCategoriaIngrediente(@RequestBody CategoriaIngredienteRequestDTO dto) {
        try {
            CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.atualizarCategoriaIngrediente(dto);
            return ResponseEntity.ok(categoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    @Operation(summary = "Deleta uma categoria de ingrediente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> deletarCategoriaIngrediente(@Parameter(description = "ID da categoria a ser deletada") @PathVariable Long id) {
        try {
            categoriaIngredienteService.deletarCategoriaIngrediente(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Lista todas as categorias de ingredientes cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaIngredienteResponseDTO>> listarCategorias() {
        List<CategoriaIngredienteResponseDTO> categorias = categoriaIngredienteService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Busca uma categoria de ingrediente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@Parameter(description = "ID da categoria a ser buscada") @PathVariable Long id) {
        try {
            CategoriaIngredienteResponseDTO categoria = categoriaIngredienteService.buscarPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
