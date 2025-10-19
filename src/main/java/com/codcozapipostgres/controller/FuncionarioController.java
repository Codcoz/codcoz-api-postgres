package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoquista")
public class FuncionarioController {
    public final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/buscar/{email}")
    public ResponseEntity<FuncionarioResponseDTO> buscarEstoquista(@PathVariable String email){
        FuncionarioResponseDTO estoquista = funcionarioService.buscarEstoquistaPorEmail(email);
        return ResponseEntity.ok(estoquista);
    }
}
