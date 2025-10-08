package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoquista")
public class FuncionarioController {
    public final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<FuncionarioResponseDTO> buscarEstoquista(@RequestParam String email){
        try{
            FuncionarioResponseDTO estoquista = funcionarioService.findEstoquistaByEmail(email);
            return ResponseEntity.ok(estoquista);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
