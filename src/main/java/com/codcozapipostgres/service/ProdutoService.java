package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.ProdutoRequestDTO;
import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.model.Produto;
import com.codcozapipostgres.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    public ProdutoService(ProdutoRepository produtoRepository, ObjectMapper objectMapper) {
        this.produtoRepository = produtoRepository;
        this.objectMapper = objectMapper;
    }
    private Produto fromRequestDTO(ProdutoRequestDTO produtoRequestDTO) {
        return objectMapper.convertValue(produtoRequestDTO, Produto.class);
    }
    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        return objectMapper.convertValue(produto, ProdutoResponseDTO.class);
    }

    public Integer quantidadeProdutosEstoque(Integer idEmpresa){
        Integer quantidade = produtoRepository.contarProdutosEmEstoque(idEmpresa);
        if (quantidade == null){
            throw new EntityNotFoundException("Empresa não cadastrada.");
        }
        return quantidade;
    }

    public Integer quantidadeProdutosEstoqueBaixo(Integer idEmpresa){
        Integer quantidade = produtoRepository.contarProdutosBaixoEstoque(idEmpresa);
        if (quantidade == null){
            throw new EntityNotFoundException("Empresa não cadastrada.");
        }
        return quantidade;
    }

    public Integer quantidadeProdutosProximosValidade(Integer idEmpresa){
        Integer quantidade = produtoRepository.contarProdutosProximosValidade(idEmpresa);
        if (quantidade == null){
            throw new EntityNotFoundException("Empresa não cadastrada.");
        }
        return quantidade;
    }
}
