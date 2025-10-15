package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorData(@PathVariable String email,@RequestParam LocalDate data) {
        try {
            List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefaPorData(data, email);
            return ResponseEntity.ok(tarefas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/buscar-periodo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPeriodo(@PathVariable String email,@RequestParam(name = "data_inicio") LocalDate dataInicio, @RequestParam(name="data_fim") LocalDate dataFim) {
        try {
            List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefaPorPeriodo(dataInicio,dataFim, email);
            return ResponseEntity.ok(tarefas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar-por-tipo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorTipo(@PathVariable String email,@RequestParam(name="data_inicio") LocalDate dataInicio, @RequestParam(name="data_fim") LocalDate dataFim, @RequestParam String tipo) {
        try{
            List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefaPorTipo(dataInicio,dataFim,email,tipo);
            return ResponseEntity.ok(tarefas);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<String> finalizarTarefa(@PathVariable Integer id) {
        try {
            tarefaService.finalizarTarefa(id);
            return ResponseEntity.ok("Tarefa finalizada com sucesso.");
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao finalizar tarefa: " + e.getMessage());
        }
    }
}
