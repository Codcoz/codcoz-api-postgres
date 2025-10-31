package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.FuncionarioRequestDTO;
import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Tag(name = "Funcionario", description = "Operações para gerenciar os funcionários da empresa.")
@Tag(name="Funcionario",description = "Operações para gerenciar os funcionários da empresa.")
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(
            summary = "Busca um funcionário pelo e-mail",
            description = "Retorna os dados do funcionário com base no e-mail informado."
    )
    @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
            content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/buscar/{email}")
    public ResponseEntity<FuncionarioResponseDTO> buscarFuncionario(
            @Parameter(description = "E-mail do funcionário", example = "maria.souza@gmail.com")
            @PathVariable String email) {
        return ResponseEntity.ok(funcionarioService.buscarPorEmail(email));
    }

    @Operation(
            summary = "Insere um novo funcionário",
            description = "Cria um novo registro de funcionário com as informações fornecidas."
    )
    @ApiResponse(responseCode = "200", description = "Funcionário inserido com sucesso",
            content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/inserir")
    public ResponseEntity<FuncionarioResponseDTO> inserirFuncionario(
            @RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
        return ResponseEntity.ok(funcionarioService.inserirFuncionario(funcionarioRequestDTO));
    }

    @Operation(
            summary = "Atualiza os dados de um funcionário existente",
            description = "Atualiza as informações de um funcionário com base em um ID já cadastrado."
    )
    @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(
            @Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id,
            @RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
        return ResponseEntity.ok(funcionarioService.atualizarFuncionario(id, funcionarioRequestDTO));
    }

    @Operation(
            summary = "Demite um funcionário",
            description = "Altera o status do funcionário para 'INATIVO' (não remove do banco)."
    )
    @ApiResponse(responseCode = "200", description = "Funcionário demitido com sucesso",
            content = @Content(schema = @Schema(implementation = FuncionarioResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/demitir/{id}")
    public ResponseEntity<FuncionarioResponseDTO> demitirFuncionario(
            @Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.desligarFuncionario(id));
    }
}