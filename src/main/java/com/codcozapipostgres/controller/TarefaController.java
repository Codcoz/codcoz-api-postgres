package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.NotFound;
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
    @GetMapping("/buscar-data")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorData(@RequestParam LocalDate data, @RequestParam String email) {
        try {
            List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefaPorData(data, email);
            return ResponseEntity.ok(tarefas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/buscar-periodo")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPeriodo(@RequestParam(name = "data_inicio") LocalDate dataInicio, @RequestParam(name="data_fim") LocalDate dataFim, @RequestParam String email) {
        try {
            List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefaPorPeriodo(dataInicio,dataFim, email);
            return ResponseEntity.ok(tarefas);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar-tipo")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorTipo(@RequestParam(name="data_inicio") LocalDate dataInicio, @RequestParam(name="data_fim") LocalDate dataFim, @RequestParam String email, @RequestParam String tipo) {
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
