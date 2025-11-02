package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.IngredienteRequestDTO;
import com.codcozapipostgres.dto.IngredienteResponseDTO;
import com.codcozapipostgres.model.Ingrediente;
import com.codcozapipostgres.repository.IngredienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteService {
    private final IngredienteRepository ingredienteRepository;
    private final ObjectMapper objectMapper;

    public IngredienteService(IngredienteRepository ingredienteRepository, ObjectMapper objectMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.objectMapper = objectMapper;
    }

    public Ingrediente fromRequestDto(IngredienteRequestDTO ingredienteRequestDTO){
        return  objectMapper.convertValue(ingredienteRequestDTO, Ingrediente.class);
    }
    public IngredienteResponseDTO toResponseDto(Ingrediente ingrediente){
        return objectMapper.convertValue(ingrediente, IngredienteResponseDTO.class);
    }

    @Transactional
    public IngredienteResponseDTO criaIngrediente(IngredienteRequestDTO ingredienteRequestDTO){
        Ingrediente ingrediente = fromRequestDto(ingredienteRequestDTO);
        ingredienteRepository.save(ingrediente);
        return toResponseDto(ingrediente);
    }

    @Transactional
    public IngredienteResponseDTO atualizaIngrediente(Long id, IngredienteRequestDTO ingredienteRequestDTO){
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente não encontrado"));
        if (ingredienteRequestDTO.getNome()!=null){
            ingrediente.setNome(ingredienteRequestDTO.getNome());
        }
        if (ingredienteRequestDTO.getDescricao()!=null){
            ingrediente.setDescricao(ingredienteRequestDTO.getDescricao());
        }
        if (ingredienteRequestDTO.getCategoriaIngrediente()!=null){
            ingrediente.setCategoriaIngrediente(ingredienteRequestDTO.getCategoriaIngrediente());
        }
        if (ingredienteRequestDTO.getQuantidadeMinima()!=null){
            ingrediente.setQuantidadeMinima(ingredienteRequestDTO.getQuantidadeMinima());
        }
        ingredienteRepository.save(ingrediente);
        return toResponseDto(ingrediente);
    }

    @Transactional
    public void deletaIngrediente(Long id) {
        if (ingredienteRepository.existsById(id)) {
            ingredienteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Ingrediente não encontrado");
        }
    }

    public List<IngredienteResponseDTO> listaIngredientes(Long empresaId) {
        List<Ingrediente> ingredientes = ingredienteRepository.listaPorEmpresa(empresaId);
        return ingredientes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public IngredienteResponseDTO buscaIngrediente(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente não encontrado"));
        return toResponseDto(ingrediente);
    }
}