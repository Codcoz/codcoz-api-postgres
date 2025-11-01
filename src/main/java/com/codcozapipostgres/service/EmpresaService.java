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
    public EmpresaResponseDTO buscaEmpresaPorCnpj(String cnpj) {
        try{
            Empresa empresa = empresaRepository.buscaEmpresaPorCnpj(cnpj);
            return toResponseDTO(empresa);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Empresa não encontrada.");
        }
    }
    @Transactional
    public void deletaEmpresaPorCnpj(String cnpj) {
        try{
            Empresa empresa = empresaRepository.buscaEmpresaPorCnpj(cnpj);
            empresaRepository.delete(empresa);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Empresa não encontrada.");
        }
    }
    public Double calculaOcupacaoEstoque(Integer idEmpresa){
        try {
            return (double) Math.round(empresaRepository.calculaPorcentagemOcupacao(idEmpresa)*100)/100;
        }catch (NullPointerException e){
            throw new EntityNotFoundException("Empresa não encontrada");
        }
    }
}
