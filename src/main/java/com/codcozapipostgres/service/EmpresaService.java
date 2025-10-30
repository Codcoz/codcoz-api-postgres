package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.EmpresaRequestDTO;
import com.codcozapipostgres.dto.EmpresaResponseDTO;
import com.codcozapipostgres.model.Empresa;
import com.codcozapipostgres.repository.EmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ObjectMapper objectMapper;

    public EmpresaService(EmpresaRepository empresaRepository, ObjectMapper objectMapper) {
        this.empresaRepository = empresaRepository;
        this.objectMapper = objectMapper;
    }

    public Empresa fromRequestDTO(EmpresaRequestDTO empresaRequestDTO) {
        return objectMapper.convertValue(empresaRequestDTO, Empresa.class);
    }

    public EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        return objectMapper.convertValue(empresa, EmpresaResponseDTO.class);
    }

    @Transactional
    public EmpresaResponseDTO criaEmpresa(EmpresaRequestDTO empresaRequestDTO) {
        Empresa empresa = fromRequestDTO(empresaRequestDTO);
        empresa.setStatus("Ativo");
        empresaRepository.save(empresa);
        return toResponseDTO(empresa);
    }
    public EmpresaResponseDTO buscarEmpresaPorCnpj(String cnpj) {
        Empresa empresa = empresaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Empresa com CNPJ " + cnpj + " não encontrada."));
        return toResponseDTO(empresa);
    }
    @Transactional
    public void deletarEmpresaPorCnpj(String cnpj) {
        Empresa empresa = empresaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Empresa com CNPJ " + cnpj + " não encontrada para deleção."));
        empresaRepository.delete(empresa);
    }
}
