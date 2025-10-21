package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    public final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping("/buscar-data/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorData(@PathVariable String email, @RequestParam LocalDate data) {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorData(data, email);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/buscar-periodo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPeriodo(@PathVariable String email, @RequestParam(name = "data_inicio") LocalDate dataInicio, @RequestParam(name = "data_fim") LocalDate dataFim) {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorPeriodo(dataInicio, dataFim, email);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/buscar-por-tipo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorTipo(@PathVariable String email, @RequestParam(name = "data_inicio") LocalDate dataInicio, @RequestParam(name = "data_fim") LocalDate dataFim, @RequestParam String tipo) {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorPeriodoETipo(dataInicio, dataFim, email, tipo);
        return ResponseEntity.ok(tarefas);
    }

    @PutMapping("/finalizar-tarefa/{id}")
    public ResponseEntity<String> finalizarTarefa(@PathVariable Integer id) {
        tarefaService.finalizarTarefa(id);
        return ResponseEntity.ok("Tarefa finalizada com sucesso.");
    }
}