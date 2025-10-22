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

import java.net.URI;
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
            Integer quantidade = produtoRepository.contarEstoque(idEmpresa);
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
            Integer quantidade = produtoRepository.contarBaixoEstoque(idEmpresa);
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
            Integer quantidade = produtoRepository.contarProximosValidade(idEmpresa);
            if (quantidade == null) {
                throw new EntityNotFoundException("Empresa não encontrada ou sem produtos cadastrados.");
            }
            return quantidade;
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    public List<ProdutoResponseDTO> listarEstoqueBaixo(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listarEstoqueBaixo(idEmpresa);
        if (produtos == null || produtos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto com estoque baixo encontrado para esta empresa.");
        }
        return produtos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarProximosValidade(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listarProximoValidade(idEmpresa);
        if (produtos == null || produtos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto próximo da validade encontrado para esta empresa.");
        }
        return produtos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarEstoque(Integer idEmpresa) {
        List<Produto> produtos = produtoRepository.listarEstoque(idEmpresa);
        if (produtos == null || produtos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum produto encontrado no estoque para esta empresa.");
        }
        return produtos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorCodigoEan(String codigoEan) {
        Produto produto = produtoRepository.buscarPorCodigoEan(codigoEan);
        if (produto == null) {
            throw new EntityNotFoundException("Produto com código EAN " + codigoEan + " não encontrado.");
        }
        return toResponseDTO(produto);
    }

    public void novaEntrada(String codigoEan, Integer quantidade) {
        try {
            produtoRepository.movimentaProdutos(codigoEan, quantidade);
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao registrar nova entrada de produto com código EAN " + codigoEan, e);
        }
    }

    public void novaBaixa(String codigoEan, Integer quantidade) {
        try {
            Integer quantNegativa = -Math.abs(quantidade);
            produtoRepository.movimentaProdutos(codigoEan, quantNegativa);
        } catch (DataAccessException e) {
            throw new PersistenceException("Erro ao registrar baixa de produto com código EAN " + codigoEan, e);
        }
    }
}
