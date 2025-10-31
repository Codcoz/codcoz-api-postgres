package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.CategoriaIngredienteRequestDTO;
import com.codcozapipostgres.dto.CategoriaIngredienteResponseDTO;
import com.codcozapipostgres.model.CategoriaIngrediente;
import com.codcozapipostgres.repository.CategoriaIngredienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaIngredienteService {

    private final CategoriaIngredienteRepository categoriaIngredienteRepository;
    private final ObjectMapper objectMapper;

    public CategoriaIngredienteService(CategoriaIngredienteRepository categoriaIngredienteRepository, ObjectMapper objectMapper) {
        this.categoriaIngredienteRepository = categoriaIngredienteRepository;
        this.objectMapper = objectMapper;
    }

    public CategoriaIngrediente fromRequestDto(CategoriaIngredienteRequestDTO dto) {
        return objectMapper.convertValue(dto, CategoriaIngrediente.class);
    }

    public CategoriaIngredienteResponseDTO toResponseDto(CategoriaIngrediente categoriaIngrediente) {
        return objectMapper.convertValue(categoriaIngrediente, CategoriaIngredienteResponseDTO.class);
    }

    @Transactional
    public CategoriaIngredienteResponseDTO criarCategoriaIngrediente(CategoriaIngredienteRequestDTO dto) {
        CategoriaIngrediente categoria = fromRequestDto(dto);
        categoriaIngredienteRepository.save(categoria);
        return toResponseDto(categoria);
    }

    @Transactional
    public CategoriaIngredienteResponseDTO atualizarCategoriaIngrediente(Long id, CategoriaIngredienteRequestDTO dto) {
        CategoriaIngrediente categoriaExistente = categoriaIngredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (dto.getNome() != null) {
            categoriaExistente.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            categoriaExistente.setDescricao(dto.getDescricao());
        }

        CategoriaIngrediente categoriaAtualizada = categoriaIngredienteRepository.save(categoriaExistente);
        return toResponseDto(categoriaAtualizada);
    }


    @Transactional
    public void deletarCategoriaIngrediente(Long id) {
        if (categoriaIngredienteRepository.existsById(id)) {
            categoriaIngredienteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Categoria não encontrada");
        }
    }

    public List<CategoriaIngredienteResponseDTO> listarTodas() {
        List<CategoriaIngrediente> categorias = categoriaIngredienteRepository.findAll();
        return categorias.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public CategoriaIngredienteResponseDTO buscarPorId(Long id) {
        CategoriaIngrediente categoria = categoriaIngredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return toResponseDto(categoria);
    }
}
