package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.FuncionarioRequestDTO;
import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    public final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/buscar/{email}")
    public ResponseEntity<FuncionarioResponseDTO> buscarFuncionario(@PathVariable String email){
        FuncionarioResponseDTO estoquista = funcionarioService.buscarPorEmail(email);
        return ResponseEntity.ok(estoquista);
    }
    @PostMapping("/inserir")
    public ResponseEntity<FuncionarioResponseDTO> inserirFuncionario(@RequestBody FuncionarioRequestDTO funcionarioRequestDTO){
        FuncionarioResponseDTO estoquista = funcionarioService.inserirFuncionario(funcionarioRequestDTO);
        return ResponseEntity.ok(estoquista);
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioResponseDTO>  atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioRequestDTO funcionarioRequestDTO){
        FuncionarioResponseDTO funcionario = funcionarioService.atualizarFuncionario(id,funcionarioRequestDTO);
        return ResponseEntity.ok(funcionario);
    }
    @PutMapping("/demitir/{id}")
    public ResponseEntity<FuncionarioResponseDTO> demitirFuncionario(@PathVariable Long id){
        FuncionarioResponseDTO funcionario = funcionarioService.desligarFuncionario(id);
        return ResponseEntity.ok(funcionario);
    }
}
