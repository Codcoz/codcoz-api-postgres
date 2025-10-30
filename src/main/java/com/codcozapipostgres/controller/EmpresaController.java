package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.EmpresaRequestDTO;
import com.codcozapipostgres.dto.EmpresaResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RequestMapping("/empresa")
@RestController
@Tag(name = "Empresa", description = "Operações para gerenciar informações da empresa.")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @Operation(summary = "Cadastra uma nova empresa",
            description = "Cria um novo registro de empresa com base nas informações fornecidas no corpo da requisição.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro de validação",
                                    value = """
                                            {
                                              "erro": "Dados inválidos.",
                                              "descricao": "Campos obrigatórios não foram preenchidos corretamente.",
                                              "status": 400
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao salvar empresa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro inesperado ao tentar salvar a empresa no banco de dados.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @PostMapping("/inserir")
    public ResponseEntity<EmpresaResponseDTO> inserirEmpresa(@RequestBody EmpresaRequestDTO empresaRequestDTO) {
        EmpresaResponseDTO empresaCriada = empresaService.criaEmpresa(empresaRequestDTO);
        return ResponseEntity.ok(empresaCriada);
    }

    @Operation(summary = "Busca uma empresa pelo CNPJ",
            description = "Retorna os dados da empresa com base no CNPJ informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Empresa não encontrada",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Empresa com CNPJ 99.999.999/0001-99 não encontrada.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro inesperado ao buscar empresa.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @GetMapping("/buscar/{cnpj}")
    public ResponseEntity<EmpresaResponseDTO> buscarEmpresa(
            @Parameter(name = "cnpj", description = "CNPJ da empresa (somente números)", example = "12345678000199")
            @PathVariable String cnpj) {

        EmpresaResponseDTO empresa = empresaService.buscarEmpresaPorCnpj(cnpj);
        return ResponseEntity.ok(empresa);
    }

    @Operation(summary = "Deleta uma empresa pelo CNPJ",
            description = "Remove o registro da empresa no banco de dados com base no CNPJ.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa deletada com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Deleção Sucesso",
                                    value = """
                                            {
                                              "mensagem": "Empresa com CNPJ 12345678000199 deletada com sucesso."
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada para deleção",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Empresa não encontrada",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Empresa com CNPJ 99.999.999/0001-99 não encontrada para deleção.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao deletar empresa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro ao tentar excluir a empresa do banco de dados.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @DeleteMapping("/deletar/{cnpj}")
    public ResponseEntity<Map<String, String>> deletarEmpresa(
            @Parameter(name = "cnpj", description = "CNPJ da empresa (somente números)", example = "12345678000199")
            @PathVariable String cnpj) {

        empresaService.deletarEmpresaPorCnpj(cnpj);

        Map<String, String> response = Map.of("mensagem", "Empresa com CNPJ " + cnpj + " deletada com sucesso.");
        return ResponseEntity.ok(response);
    }
}