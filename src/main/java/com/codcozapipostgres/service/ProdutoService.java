package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.ProdutoRequestDTO;
import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.model.Produto;
import com.codcozapipostgres.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProdutoResponseDTO> listarProdutosEstoqueBaixo(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listarEstoqueBaixo(idEmpresa);
        return produtos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarProdutosProximosValidade(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listarProximoValidade(idEmpresa);
        return produtos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarProdutosEstoque(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listarEstoque(idEmpresa);
        return produtos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarProdutoPorCodigoEan(Long codigoEan) {
        return toResponseDTO(produtoRepository.buscarProdutoPorCodigoEan(codigoEan));
    }
}
