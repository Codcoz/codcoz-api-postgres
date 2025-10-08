package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.FuncionarioRequestDTO;
import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.model.Funcionario;
import com.codcozapipostgres.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, ObjectMapper objectMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.objectMapper = objectMapper;
    }

    private Funcionario fromRequestDTO(FuncionarioRequestDTO funcionarioRequestDTO) {
        return objectMapper.convertValue(funcionarioRequestDTO, Funcionario.class);
    }
    private FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        return objectMapper.convertValue(funcionario, FuncionarioResponseDTO.class);
    }

    public FuncionarioResponseDTO findEstoquistaByEmail (String email){
        Funcionario funcionario = funcionarioRepository.findByEmail(email);
        if(funcionario == null){
            throw new EntityNotFoundException("Estoquista não registado no banco.");
        }
        return toResponseDTO(funcionario);
    }
}
