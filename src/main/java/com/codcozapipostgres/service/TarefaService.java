package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.TarefaRequestDTO;
import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.model.Tarefa;
import com.codcozapipostgres.projection.TarefaProjection;
import com.codcozapipostgres.repository.TarefaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private TarefaResponseDTO toResponseDTO(TarefaProjection projection) {
        return new TarefaResponseDTO(
                projection.getId(),
                projection.getEmpresa(),
                projection.getTipoTarefa(),
                projection.getIngrediente(),
                projection.getRelator(),
                projection.getResponsavel(),
                projection.getPedido(),
                projection.getSituacao(),
                projection.getQuantidadeEsperada(),
                projection.getContagem(),
                projection.getDataCriacao(),
                projection.getDataLimite(),
                projection.getDataConclusao()
        );
    }
    public List<TarefaResponseDTO> buscarPorData(LocalDate data, String email) {
        List<TarefaProjection> tarefas = tarefaRepository.buscarPorData(data, email);
        return tarefas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim, String email) {
        List<TarefaProjection> tarefas = tarefaRepository.buscarPorPeriodo(dataInicio, dataFim, email);
        return tarefas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> buscarPorPeriodoETipo(LocalDate dataInicio, LocalDate dataFim, String email, String tipo) {
        List<TarefaProjection> tarefas = tarefaRepository.buscarPorPeriodoETipo(dataInicio, dataFim, email, tipo);
        return tarefas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public void finalizarTarefa(Integer idTarefa) {
        try {
            tarefaRepository.finalizarTarefa(idTarefa);
        }
        catch (UncategorizedSQLException e) {
            String mensagem = Objects.requireNonNull(e.getSQLException()).getMessage();
            if (mensagem.contains("não encontrado")) {
                throw new EntityNotFoundException("Tarefa não encontrada");
            }
            throw new RuntimeException("Erro ao executar procedure: " + mensagem, e);
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Erro de acesso ao banco de dados: " + e.getMessage(), e);
        }
    }
}
