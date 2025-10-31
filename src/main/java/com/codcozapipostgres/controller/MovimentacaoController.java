package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.MovimentacaoRequestDTO;
import com.codcozapipostgres.dto.MovimentacaoResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.MovimentacaoService;
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
@Tag(name="Movimentação", description = "Operações para gerenciar as movimentações de produtos da empresa.")
@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @Operation(summary = "Lista todas as movimentações",
            description = "Retorna todas as movimentações cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de movimentações",
            content = @Content(schema = @Schema(implementation = MovimentacaoResponseDTO.class)))
    @GetMapping("/listar")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarMovimentacoes() {
        return ResponseEntity.ok(movimentacaoService.listarTodos());
    }

    @Operation(summary = "Busca uma movimentação pelo ID",
            description = "Retorna os dados de uma movimentação específica com base no ID informado.")
    @ApiResponse(responseCode = "200", description = "Movimentação encontrada",
            content = @Content(schema = @Schema(implementation = MovimentacaoResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/buscar/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> buscarMovimentacao(
            @Parameter(description = "ID da movimentação", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(movimentacaoService.buscarPorId(id));
    }

    @Operation(summary = "Lista entradas por empresa",
            description = "Retorna todas as movimentações do tipo entrada de uma empresa específica.")
    @GetMapping("/entradas/empresa/{idEmpresa}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarEntradasPorEmpresa(
            @Parameter(description = "ID da empresa", example = "1") @PathVariable Long idEmpresa) {
        return ResponseEntity.ok(movimentacaoService.listarEntradasPorEmpresa(idEmpresa));
    }

    @Operation(summary = "Lista baixas por empresa",
            description = "Retorna todas as movimentações do tipo baixa de uma empresa específica.")
    @GetMapping("/baixas/empresa/{idEmpresa}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarBaixasPorEmpresa(
            @Parameter(description = "ID da empresa", example = "1") @PathVariable Long idEmpresa) {
        return ResponseEntity.ok(movimentacaoService.listarBaixasPorEmpresa(idEmpresa));
    }

    @Operation(summary = "Lista entradas por empresa em período",
            description = "Retorna todas as entradas de uma empresa dentro de um período de datas específico.")
    @GetMapping("/entradas/empresa/{idEmpresa}/periodo")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarEntradasPorEmpresaPorPeriodo(
            @Parameter(description = "ID da empresa", example = "1") @PathVariable Long idEmpresa,
            @Parameter(description = "Data inicial (YYYY-MM-DD)", example = "2025-01-01") @RequestParam String dataInicio,
            @Parameter(description = "Data final (YYYY-MM-DD)", example = "2025-01-31") @RequestParam String dataFim) {
        return ResponseEntity.ok(movimentacaoService.listarEntradasPorEmpresaPorPeriodo(idEmpresa, dataInicio, dataFim));
    }

    @Operation(summary = "Lista baixas por empresa em período",
            description = "Retorna todas as baixas de uma empresa dentro de um período de datas específico.")
    @GetMapping("/baixas/empresa/{idEmpresa}/periodo")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarBaixasPorEmpresaPorPeriodo(
            @Parameter(description = "ID da empresa", example = "1") @PathVariable Long idEmpresa,
            @Parameter(description = "Data inicial (YYYY-MM-DD)", example = "2025-01-01") @RequestParam String dataInicio,
            @Parameter(description = "Data final (YYYY-MM-DD)", example = "2025-01-31") @RequestParam String dataFim) {
        return ResponseEntity.ok(movimentacaoService.listarBaixasPorEmpresaPorPeriodo(idEmpresa, dataInicio, dataFim));
    }
}
