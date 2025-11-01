package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.ProdutoRequestDTO;
import com.codcozapipostgres.dto.ProdutoResponseDTO;
import com.codcozapipostgres.model.Produto;
import com.codcozapipostgres.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataAccessException;
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
        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado.");
        }
        return objectMapper.convertValue(produto, ProdutoResponseDTO.class);
    }

    public Integer quantidadeEstoque(Integer idEmpresa) {
        try {
            Integer quantidade = produtoRepository.contaEstoque(idEmpresa);
            if (quantidade == null) {
                throw new EntityNotFoundException("Empresa não encontrada ou sem produtos cadastrados.");
            }
            return quantidade;
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    public Integer quantidadeEstoqueBaixo(Integer idEmpresa) {
        try {
            Integer quantidade = produtoRepository.contaBaixoEstoque(idEmpresa);
            if (quantidade == null) {
                throw new EntityNotFoundException("Empresa não encontrada ou sem produtos cadastrados.");
            }
            return quantidade;
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    public Integer quantidadeProximosValidade(Integer idEmpresa) {
        try {
            Integer quantidade = produtoRepository.contaProximosValidade(idEmpresa);
            if (quantidade == null) {
                throw new EntityNotFoundException("Empresa não encontrada ou sem produtos cadstrados.");
            }
            return quantidade;
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    public List<ProdutoResponseDTO> listaEstoqueBaixo(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listaEstoqueBaixo(idEmpresa);
        if (produtos == null || produtos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto com estoque baixo.");
        }
        return produtos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listaProximosValidade(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listaProximoValidade(idEmpresa);
        if (produtos == null || produtos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto próximo à validade.");
        }
        return produtos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listaEstoque(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listaEstoque(idEmpresa);
        if (produtos == null || produtos.isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado.");
        }
        return produtos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscaPorCodigoEan(String codigoEan) {
        Produto produto = produtoRepository.buscaPorCodigoEan(codigoEan);
        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado.");
        }
        return toResponseDTO(produto);
    }

    public void novaEntrada(String codigoEan, Integer quantidade) {
        try {
            produtoRepository.movimentaProdutos(codigoEan, quantidade);
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao registrar nova entrada de produto", e);
        }
    }

    public void novaBaixa(String codigoEan, Integer quantidade) {
        try {
            Integer quantNegativa = -Math.abs(quantidade);
            produtoRepository.movimentaProdutos(codigoEan, quantNegativa);
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao registrar baixa de produto", e);
        }
    }
}