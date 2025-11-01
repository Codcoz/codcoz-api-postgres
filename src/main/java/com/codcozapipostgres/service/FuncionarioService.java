package com.codcozapipostgres.service;

import com.codcozapipostgres.dto.FuncionarioRequestDTO;
import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.model.Funcionario;
import com.codcozapipostgres.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public FuncionarioResponseDTO criaFuncionario(FuncionarioRequestDTO funcionarioRequestDTO){
        Funcionario funcionario = fromRequestDTO(funcionarioRequestDTO);
        funcionario.setDataContratacao(LocalDate.now());
        funcionario.setStatus("Ativo");
        Funcionario estoquistaAdicionado =  funcionarioRepository.save(funcionario);
        return toResponseDTO(estoquistaAdicionado);
    }

    @Transactional
    public FuncionarioResponseDTO atualizaFuncionario(Long id, FuncionarioRequestDTO funcionarioRequestDTO){
        Funcionario funcionario = funcionarioRepository.buscaPorId(id);
        if(funcionario == null){
            throw new EntityNotFoundException("Funcionário não encontrado");
        }
        if(funcionarioRequestDTO != null){
            if (funcionarioRequestDTO.getEmpresaId() != null){
                funcionario.setEmpresaId(funcionarioRequestDTO.getEmpresaId());
            }
            if (funcionarioRequestDTO.getFuncaoId() != null){
                funcionario.setFuncaoId(funcionarioRequestDTO.getFuncaoId());
            }
            if (funcionarioRequestDTO.getNome()!= null){
                funcionario.setNome(funcionarioRequestDTO.getNome());
            }
            if (funcionarioRequestDTO.getSobrenome()!= null){
                funcionario.setSobrenome(funcionarioRequestDTO.getSobrenome());
            }
            if (funcionarioRequestDTO.getEmail()!= null){
                funcionario.setEmail(funcionarioRequestDTO.getEmail());
            }
            Funcionario atualizado =  funcionarioRepository.save(funcionario);
            return toResponseDTO(atualizado);
        }else {
            throw new NullPointerException("Nenhuma informação foi inserida para atualização.");
        }
    }

    @Transactional
    public FuncionarioResponseDTO desligaFuncionario(Long id){
        Funcionario funcionario = funcionarioRepository.buscaPorId(id);
        if(funcionario == null){
            throw new EntityNotFoundException("Funcionário não encontrado");
        }
        funcionario.setStatus("Inativo");
        funcionarioRepository.save(funcionario);
        return toResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO buscaFuncionario(String email){
        Funcionario funcionario = funcionarioRepository.buscaPorEmail(email);
        if(funcionario == null){
            throw new EntityNotFoundException("Estoquista não registado no banco.");
        }
        return toResponseDTO(funcionario);
    }

    public List<FuncionarioResponseDTO> listaFuncionarios(Integer empresaId){
        List<Funcionario> funcionarios = funcionarioRepository.buscaPorEmpresaId(empresaId);
        return funcionarios.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
