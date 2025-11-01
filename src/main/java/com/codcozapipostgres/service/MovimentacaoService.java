package com.codcozapipostgres.service;

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

    public List<MovimentacaoResponseDTO> listaMovimentacoes(Long idEmpresa) {
        return movimentacaoRepository.listaMovimentacoesPorEmpresa(idEmpresa).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MovimentacaoResponseDTO buscaMovimentacao(Long id) {
        Movimentacao movimentacao = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada"));
        return toResponseDTO(movimentacao);
    }

    public List<MovimentacaoResponseDTO> listaEntradas(Long idEmpresa) {
        return movimentacaoRepository.listaEntradasPorEmpresa(idEmpresa)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public List<MovimentacaoResponseDTO> listaBaixas(Long idEmpresa) {
        return movimentacaoRepository.listaBaixasPorEmpresa(idEmpresa)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MovimentacaoResponseDTO> listaEntradasPorPeriodo(Long idEmpresa, String dataInicio, String dataFim) {
        return movimentacaoRepository.listaEntradasPorEmpresaPorPeriodo(idEmpresa, dataInicio, dataFim)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MovimentacaoResponseDTO> listaBaixasPorPeriodo(Long idEmpresa, String dataInicio, String dataFim) {
        return movimentacaoRepository.listaBaixasPorEmpresaPorPeriodo(idEmpresa, dataInicio, dataFim)
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}

