package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@Tag(name = "Tarefa", description = "Operações para gerenciar as tarefas a serem realizadas pelos estoquistas.")
@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(
            summary = "Busca tarefas por data e e-mail",
            description = "Retorna todas as tarefas associadas ao funcionário em uma data específica.",
            parameters = {
                    @Parameter(name = "email", description = "E-mail do funcionário", example = "joao.silva@empresa.com"),
                    @Parameter(name = "data", description = "Data desejada (yyyy-MM-dd)", example = "2025-10-23")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefas encontradas", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/buscar-data/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorData(@PathVariable String email, @RequestParam LocalDate data) {
        return ResponseEntity.ok(tarefaService.buscarPorData(data, email));
    }

    @Operation(
            summary = "Busca tarefas por período e e-mail",
            description = "Retorna todas as tarefas associadas ao funcionário em um intervalo de datas.",
            parameters = {
                    @Parameter(name = "email", description = "E-mail do funcionário", example = "ana.santos@empresa.com"),
                    @Parameter(name = "data_inicio", description = "Data inicial (yyyy-MM-dd)", example = "2025-10-01"),
                    @Parameter(name = "data_fim", description = "Data final (yyyy-MM-dd)", example = "2025-10-23")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefas encontradas", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/buscar-periodo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPeriodo(
            @PathVariable String email,
            @RequestParam(name = "data_inicio") LocalDate dataInicio,
            @RequestParam(name = "data_fim") LocalDate dataFim) {
        return ResponseEntity.ok(tarefaService.buscarPorPeriodo(dataInicio, dataFim, email));
    }

    @Operation(
            summary = "Busca tarefas por tipo e período",
            description = "Filtra tarefas do funcionário por tipo e intervalo de datas.",
            parameters = {
                    @Parameter(name = "email", description = "E-mail do funcionário", example = "maria.souza@empresa.com"),
                    @Parameter(name = "data_inicio", description = "Data inicial (yyyy-MM-dd)", example = "2025-10-01"),
                    @Parameter(name = "data_fim", description = "Data final (yyyy-MM-dd)", example = "2025-10-23"),
                    @Parameter(name = "tipo", description = "Tipo da tarefa", example = "Conferência de estoque")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefas encontradas", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/buscar-por-tipo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorTipo(
            @PathVariable String email,
            @RequestParam(name = "data_inicio") LocalDate dataInicio,
            @RequestParam(name = "data_fim") LocalDate dataFim,
            @RequestParam String tipo) {
        return ResponseEntity.ok(tarefaService.buscarPorPeriodoETipo(dataInicio, dataFim, email, tipo));
    }

    @Operation(
            summary = "Finaliza uma tarefa",
            description = "Marca a tarefa como finalizada no banco de dados.",
            parameters = @Parameter(name = "id", description = "Identificador da tarefa", example = "42"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa finalizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro ao finalizar tarefa", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("/finalizar-tarefa/{id}")
    public ResponseEntity<String> finalizarTarefa(@PathVariable Integer id) {
        tarefaService.finalizarTarefa(id);
        return ResponseEntity.ok("Tarefa finalizada com sucesso.");
    }
}