package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.MovimentacaoRequestDTO;
import com.codcozapipostgres.dto.MovimentacaoResponseDTO;
import com.codcozapipostgres.model.Movimentacao;
import com.codcozapipostgres.repository.MovimentacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ObjectMapper objectMapper;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ObjectMapper objectMapper) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.objectMapper = objectMapper;
    }
    public MovimentacaoResponseDTO toResponseDTO(Movimentacao movimentacao){
        return objectMapper.convertValue(movimentacao, MovimentacaoResponseDTO.class);
    }

    public List<MovimentacaoResponseDTO> listarTodos() {
        return movimentacaoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MovimentacaoResponseDTO buscarPorId(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada"));
        return toResponseDTO(movimentacao);
    }

    public List<MovimentacaoResponseDTO> listarEntradasPorEmpresa(Long idEmpresa) {
        return movimentacaoRepository.listarEntradasPorEmpresa(idEmpresa)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MovimentacaoResponseDTO> listarBaixasPorEmpresa(Long idEmpresa) {
        return movimentacaoRepository.listarBaixasPorEmpresa(idEmpresa)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MovimentacaoResponseDTO> listarEntradasPorEmpresaPorPeriodo(Long idEmpresa, String dataInicio, String dataFim) {
        return movimentacaoRepository.listarEntradasPorEmpresaPorPeriodo(idEmpresa, dataInicio, dataFim)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MovimentacaoResponseDTO> listarBaixasPorEmpresaPorPeriodo(Long idEmpresa, String dataInicio, String dataFim) {
        return movimentacaoRepository.listarBaixasPorEmpresaPorPeriodo(idEmpresa, dataInicio, dataFim)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}

