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
    public IngredienteResponseDTO criarIngrediente(IngredienteRequestDTO ingredienteRequestDTO){
        Ingrediente ingrediente = fromRequestDto(ingredienteRequestDTO);
        ingredienteRepository.save(ingrediente);
        return toResponseDto(ingrediente);
    }
    @Transactional
    public IngredienteResponseDTO atualizarIngrediente(IngredienteRequestDTO ingredienteRequestDTO){
        Ingrediente ingrediente = fromRequestDto(ingredienteRequestDTO);
        if (ingrediente.getId()!=null && ingredienteRepository.existsById(ingrediente.getId())){
            if (ingrediente.getNome()!=null){
                ingrediente.setNome(ingrediente.getNome());
            }
            if (ingrediente.getDescricao()!=null){
                ingrediente.setDescricao(ingrediente.getDescricao());
            }
            ingredienteRepository.save(ingrediente);
            return toResponseDto(ingrediente);
        }else {
            throw new EntityNotFoundException("Nenhum ingrediente foi encontrado");
        }
    }
    @Transactional
    public void deletarIngrediente(Long id) {
        if (ingredienteRepository.existsById(id)) {
            ingredienteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Nenhum ingrediente foi encontrado");
        }
    }
    public List<IngredienteResponseDTO> listarTodos() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        return ingredientes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public IngredienteResponseDTO buscarPorId(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum ingrediente foi encontrado"));
        return toResponseDto(ingrediente);
    }

}
