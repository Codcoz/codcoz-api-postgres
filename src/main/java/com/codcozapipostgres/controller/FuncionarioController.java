package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.FuncionarioRequestDTO;
import com.codcozapipostgres.dto.FuncionarioResponseDTO;
import com.codcozapipostgres.service.FuncionarioService;
import com.codcozapipostgres.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Funcionario",description = "Operações para gerenciar os funcionários da empresa.")
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(summary = "Busca um funcionário pelo e-mail",
            description = "Retorna os dados do funcionário com base no e-mail informado.",
            parameters = {
                @Parameter(name="email",description = "email do funcionário que deseja buscar",example = "maria.souza@gmail.com")
            })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Funcionário não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Estoquista não registrado no banco.",
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
                                              "descricao": "Erro inesperado ao buscar funcionário.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @GetMapping("/buscar/{email}")
    public ResponseEntity<FuncionarioResponseDTO> buscarFuncionario(@PathVariable String email) {
        FuncionarioResponseDTO estoquista = funcionarioService.buscarPorEmail(email);
        return ResponseEntity.ok(estoquista);
    }

    @Operation(summary = "Insere um novo funcionário",
            description = "Cria um novo registro de funcionário com as informações fornecidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário inserido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no corpo da requisição",
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
            @ApiResponse(responseCode = "500", description = "Erro interno ao salvar funcionário",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro ao salvar o funcionário no banco de dados.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @PostMapping("/inserir")
    public ResponseEntity<FuncionarioResponseDTO> inserirFuncionario(@RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
        FuncionarioResponseDTO estoquista = funcionarioService.inserirFuncionario(funcionarioRequestDTO);
        return ResponseEntity.ok(estoquista);
    }

    @Operation(summary = "Atualiza os dados de um funcionário existente",
            description = "Atualiza as informações de um funcionário com base em um ID já cadastrado no banco.",
            parameters = {
                    @Parameter(name="id",description = "ID referente ao registro do funcionário no banco de dados",example = "1")
            })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Funcionário não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Funcionário não registrado no banco.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Dados inválidos",
                                    value = """
                                            {
                                              "erro": "Requisição inválida.",
                                              "descricao": "Nenhuma informação foi inserida para atualização.",
                                              "status": 400
                                            }
                                            """
                            )))
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(
            @PathVariable Long id, @RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
        FuncionarioResponseDTO funcionario = funcionarioService.atualizarFuncionario(id, funcionarioRequestDTO);
        return ResponseEntity.ok(funcionario);
    }

    @Operation(summary = "Demite um funcionário",
            description = "Altera o status do funcionário para 'INATIVO'. Não remove funcionários do banco.",
            parameters = {
                    @Parameter(name="id",description = "ID referente ao registro do funcionário no banco de dados",example = "1")
            })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário demitido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FuncionarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Funcionário não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Funcionário não registrado no banco.",
                                              "status": 404
                                            }
                                            """
                            )))
    })
    @PutMapping("/demitir/{id}")
    public ResponseEntity<FuncionarioResponseDTO> demitirFuncionario(@PathVariable Long id) {
        FuncionarioResponseDTO funcionario = funcionarioService.desligarFuncionario(id);
        return ResponseEntity.ok(funcionario);
    }
}