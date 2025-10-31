package com.codcozapipostgres.controller;

import com.codcozapipostgres.dto.TarefaResponseDTO;
import com.codcozapipostgres.exception.ErrorResponse;
import com.codcozapipostgres.service.TarefaService;
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

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@Tag(name = "Tarefa", description = "Operações para gerenciar as tarefas a serem realizadas pelos estoquistas.")
@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(
            summary = "Busca tarefas por data e e-mail do funcionário",
            description = "Retorna todas as tarefas associadas ao funcionário em uma data específica.",
            parameters = {
                    @Parameter(name = "email", description = "E-mail do funcionário responsável pela tarefa", example = "joao.silva@empresa.com"),
                    @Parameter(name = "data", description = "Data desejada para busca (formato: yyyy-MM-dd)", example = "2025-10-23")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada para o e-mail e data informados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Tarefas não encontradas",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhuma tarefa encontrada para o funcionário informado nesta data.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar tarefas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro inesperado ao buscar tarefas no banco de dados.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @GetMapping("/buscar-data/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorData(
            @PathVariable String email,
            @RequestParam LocalDate data) {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorData(data, email);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(
            summary = "Busca tarefas por período e e-mail do funcionário",
            description = "Retorna todas as tarefas associadas ao funcionário em um intervalo de datas.",
            parameters = {
                    @Parameter(name = "email", description = "E-mail do funcionário responsável pela tarefa", example = "ana.santos@empresa.com"),
                    @Parameter(name = "data_inicio", description = "Data inicial do período (formato: yyyy-MM-dd)", example = "2025-10-01"),
                    @Parameter(name = "data_fim", description = "Data final do período (formato: yyyy-MM-dd)", example = "2025-10-23")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada para o período informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Nenhuma tarefa encontrada",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhuma tarefa encontrada para o período informado.",
                                              "status": 404
                                            }
                                            """
                            )))
    })
    @GetMapping("/buscar-periodo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorPeriodo(
            @PathVariable String email,
            @RequestParam(name = "data_inicio") LocalDate dataInicio,
            @RequestParam(name = "data_fim") LocalDate dataFim) {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorPeriodo(dataInicio, dataFim, email);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(
            summary = "Busca tarefas por tipo e período",
            description = "Retorna as tarefas do funcionário filtradas por tipo e intervalo de datas.",
            parameters = {
                    @Parameter(name = "email", description = "E-mail do funcionário responsável pela tarefa", example = "maria.souza@empresa.com"),
                    @Parameter(name = "data_inicio", description = "Data inicial do período (formato: yyyy-MM-dd)", example = "2025-10-01"),
                    @Parameter(name = "data_fim", description = "Data final do período (formato: yyyy-MM-dd)", example = "2025-10-23"),
                    @Parameter(name = "tipo", description = "Tipo da tarefa", example = "Conferência de estoque")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TarefaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada com o tipo informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Tipo de tarefa não encontrado",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Nenhuma tarefa do tipo PRODUÇÃO encontrada para o funcionário informado.",
                                              "status": 404
                                            }
                                            """
                            )))
    })
    @GetMapping("/buscar-por-tipo/{email}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefaPorTipo(
            @PathVariable String email,
            @RequestParam(name = "data_inicio") LocalDate dataInicio,
            @RequestParam(name = "data_fim") LocalDate dataFim,
            @RequestParam String tipo) {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorPeriodoETipo(dataInicio, dataFim, email, tipo);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(
            summary = "Finaliza uma tarefa específica",
            description = "Marca a tarefa como finalizada no banco de dados.",
            parameters = {
                    @Parameter(name = "id", description = "Identificador da tarefa que será finalizada", example = "42")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa finalizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "\"Tarefa finalizada com sucesso.\""))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada para o ID informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Tarefa não encontrada",
                                    value = """
                                            {
                                              "erro": "Objeto não encontrado.",
                                              "descricao": "Tarefa com ID 42 não encontrada.",
                                              "status": 404
                                            }
                                            """
                            ))),
            @ApiResponse(responseCode = "500", description = "Erro ao finalizar tarefa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Erro interno",
                                    value = """
                                            {
                                              "erro": "Erro interno no servidor.",
                                              "descricao": "Erro inesperado ao finalizar a tarefa no banco de dados.",
                                              "status": 500
                                            }
                                            """
                            )))
    })
    @PutMapping("/finalizar-tarefa/{id}")
    public ResponseEntity<String> finalizarTarefa(@PathVariable Integer id) {
        tarefaService.finalizarTarefa(id);
        return ResponseEntity.ok("Tarefa finalizada com sucesso.");
    }
}