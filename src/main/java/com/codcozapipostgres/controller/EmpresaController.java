package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.EmpresaRequestDTO;
import com.codcozapipostgres.dto.EmpresaResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/empresa")
@RestController
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
}
