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
    private TarefaResponseDTO toResponseDTO(Tarefa tarefa) {
        return objectMapper.convertValue (tarefa, TarefaResponseDTO.class);
    }
    private TarefaResponseDTO fromProjection(TarefaProjection projection) {
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
    public List<TarefaResponseDTO> buscaPorData(LocalDate data, String email) {
        List<TarefaProjection> tarefas = tarefaRepository.buscaPorData(data, email);
        return tarefas.stream()
                .map(this::fromProjection)
                .collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> buscaPorPeriodo(LocalDate dataInicio, LocalDate dataFim, String email) {
        List<TarefaProjection> tarefas = tarefaRepository.buscaPorPeriodo(dataInicio, dataFim, email);
        return tarefas.stream()
                .map(this::fromProjection)
                .collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> buscaPorPeriodoETipo(LocalDate dataInicio, LocalDate dataFim, String email, String tipo) {
        List<TarefaProjection> tarefas = tarefaRepository.buscaPorPeriodoETipo(dataInicio, dataFim, email, tipo);
        return tarefas.stream()
                .map(this::fromProjection)
                .collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> buscarConcluidas(Integer dias,Long empresaId){
        try{
            LocalDate dataLimite = LocalDate.now().minusDays(dias);
            List<Tarefa> tarefas = tarefaRepository.buscaTarefaPorConclusao(dataLimite, empresaId);
            return tarefas.stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Nenhuma tarefa encontrada com esse parâmetro.");
        }
    }

    public void finalizaTarefa(Integer idTarefa) {
        try {
            tarefaRepository.finalizaTarefa(idTarefa);
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
