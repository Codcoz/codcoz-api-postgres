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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresa")
@Tag(name = "Empresa", description = "Endpoints para gerenciamento de empresas.")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @Operation(
            summary = "Cadastrar nova empresa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso",
                            content = @Content(schema = @Schema(implementation = EmpresaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                        {"erro":"Dados inválidos.","descricao":"Campos obrigatórios não foram preenchidos.","status":400}
                    """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping("/inserir")
    public ResponseEntity<EmpresaResponseDTO> inserirEmpresa(@RequestBody EmpresaRequestDTO dto) {
        return ResponseEntity.ok(empresaService.criaEmpresa(dto));
    }

    @Operation(
            summary = "Buscar empresa por CNPJ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa encontrada",
                            content = @Content(schema = @Schema(implementation = EmpresaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Empresa não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = """
                        {"erro":"Objeto não encontrado.","descricao":"Empresa com o CNPJ informado não foi localizada.","status":404}
                    """))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/buscar/{cnpj}")
    public ResponseEntity<EmpresaResponseDTO> buscarEmpresa(
            @Parameter(description = "CNPJ da empresa (somente números)", example = "12345678000199")
            @PathVariable String cnpj) {
        return ResponseEntity.ok(empresaService.buscaEmpresaPorCnpj(cnpj));
    }

    @Operation(
            summary = "Deletar empresa por CNPJ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa deletada com sucesso",
                            content = @Content(examples = @ExampleObject(value = """
                    {"mensagem":"Empresa deletada com sucesso."}
                """))),
                    @ApiResponse(responseCode = "404", description = "Empresa não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("/deletar/{cnpj}")
    public ResponseEntity<Map<String, String>> deletarEmpresa(
            @Parameter(description = "CNPJ da empresa (somente números)", example = "12345678000199")
            @PathVariable String cnpj) {

        empresaService.deletaEmpresaPorCnpj(cnpj);
        return ResponseEntity.ok(Map.of("mensagem", "Empresa com CNPJ " + cnpj + " deletada com sucesso."));
    }

    @Operation(
            summary = "Calcular ocupação do estoque da empresa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cálculo realizado com sucesso",
                            content = @Content(examples = @ExampleObject(value = """
                    {"ocupacaoEstoque":75.32}
                """))),
                    @ApiResponse(responseCode = "404", description = "Empresa não encontrada ou sem estoque",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/ocupacao-estoque/{idEmpresa}")
    public ResponseEntity<Map<String, Double>> calcularOcupacaoEstoque(
            @Parameter(description = "ID da empresa cadastrada", example = "1")
            @PathVariable Integer idEmpresa) {

        Double ocupacao = empresaService.calculaOcupacaoEstoque(idEmpresa);
        return ResponseEntity.ok(Map.of("ocupacaoEstoque", ocupacao));
    }
}
