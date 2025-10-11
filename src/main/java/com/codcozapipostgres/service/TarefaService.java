package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.TarefaRequestDTO;
import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.model.Tarefa;
import com.codcozapipostgres.repository.TarefaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final ObjectMapper objectMapper;

    public TarefaService(TarefaRepository tarefaRepository, ObjectMapper objectMapper) {
        this.tarefaRepository = tarefaRepository;
        this.objectMapper = objectMapper;
    }
    private Tarefa fromRequestDTO(TarefaRequestDTO tarefaRequestDTO) {
        return objectMapper.convertValue (tarefaRequestDTO, Tarefa.class);
    }
    private TarefaResponseDTO toResponseDTO(Object[] r) {
        Map<String, Object> map = new HashMap<>();
        map.put("empresa", r[0]);
        map.put("tipoTarefa", r[1]);
        map.put("ingrediente", r[2]);
        map.put("relator", r[3]);
        map.put("responsavel", r[4]);
        map.put("pedido", r[5]);
        map.put("situacao", r[6]);
        map.put("dataCriacao", r[7]);
        map.put("dataLimite", r[8]);
        map.put("dataConclusao", r[9]);
        return objectMapper.convertValue(map, TarefaResponseDTO.class);
    }

    public List<TarefaResponseDTO> buscarTarefaPorData(LocalDate data, String email) {
        List<Object[]> resultados = tarefaRepository.buscarPorData(data, email);

        if (resultados.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para esta data.");
        }
        return resultados.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TarefaResponseDTO> buscarTarefaPorPeriodo(LocalDate dataInicio, LocalDate dataFim, String email) {
        List<Object[]> resultados = tarefaRepository.buscarPorPeriodo(dataInicio, dataFim, email);

        if (resultados.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada para esta data.");
        }
        return resultados.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TarefaResponseDTO> buscarTarefaPorTipo(LocalDate dataInicio, LocalDate dataFim, String email, String tipo) {
        List<Object[]> resultados = tarefaRepository.buscarPorTipo(dataInicio, dataFim, email, tipo);
        if (resultados.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma tarefa encontrada.");
        }
        return resultados.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void finalizarTarefa(Integer idTarefa) {
        try {
            tarefaRepository.finalizarTarefa(idTarefa);
        }
        catch (UncategorizedSQLException e) {
            String mensagem = e.getSQLException().getMessage();
            if (mensagem.contains("não encontrado")) {
                throw new EntityNotFoundException("Tarefa com ID " + idTarefa + " não encontrada.");
            }
            throw new RuntimeException("Erro ao executar procedure: " + mensagem, e);
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Erro de acesso ao banco de dados: " + e.getMessage(), e);
        }
    }
}
