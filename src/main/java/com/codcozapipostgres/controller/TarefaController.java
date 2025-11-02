package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.TarefaRequestDTO;
import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Tarefa", description = "Operações para gerenciar as tarefas a serem realizadas pelos estoquistas.")
@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService service;
    public TarefaController(TarefaService service) { this.service = service; }

    @Operation(summary = "Busca tarefas por data e e-mail do funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas",
                    content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/buscar-data/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorData(@PathVariable String email,
                                                                       @RequestParam LocalDate data) {
        return ResponseEntity.ok(service.buscaPorData(data, email));
    }

    @Operation(summary = "Busca tarefas por período e e-mail")
    @GetMapping("/buscar-periodo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPeriodo(@PathVariable String email,
                                                                          @RequestParam LocalDate data_inicio,
                                                                          @RequestParam LocalDate data_fim) {
        return ResponseEntity.ok(service.buscaPorPeriodo(data_inicio, data_fim, email));
    }

    @Operation(summary = "Busca tarefas por tipo e período")
    @GetMapping("/buscar-por-tipo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorTipo(@PathVariable String email,
                                                                       @RequestParam LocalDate data_inicio,
                                                                       @RequestParam LocalDate data_fim,
                                                                       @RequestParam String tipo) {
        return ResponseEntity.ok(service.buscaPorPeriodoETipo(data_inicio, data_fim, email, tipo));
    }

    @Operation(summary = "Finaliza uma tarefa")
    @PutMapping("/finalizar-tarefa/{id}")
    public ResponseEntity<String> finalizarTarefa(@PathVariable Integer id) {
        service.finalizaTarefa(id);
        return ResponseEntity.ok("Tarefa finalizada com sucesso.");
    }

    @Operation(summary = "Busca tarefas concluídas nos últimos dias")
    @GetMapping("/buscar-concluidas/{empresaId}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefasConcluidas(@RequestParam Integer dias,
                                                                           @PathVariable Long empresaId) {
        return ResponseEntity.ok(service.buscaConcluidas(dias, empresaId));
    }

    @Operation(summary = "Lista todas as tarefas de uma empresa")
    @GetMapping("/listar/{empresaId}")
    public ResponseEntity<List<TarefaResponseDTO>> listaTarefas(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(service.listaTarefas(empresaId));
    }

    @Operation(summary = "Cria uma nova tarefa")
    @PostMapping("/criar")
    public ResponseEntity<TarefaResponseDTO> criaTarefa(@RequestBody TarefaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.criaTarefa(dto));
    }

    @Operation(summary = "Deleta uma tarefa pelo ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletaTarefa(@PathVariable Long id) {
        service.deletaTarefa(id);
        return ResponseEntity.ok("Tarefa deletada com sucesso.");
    }

    @Operation(summary = "Busca tarefa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscaTarefaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscaPorId(id));
    }
}
