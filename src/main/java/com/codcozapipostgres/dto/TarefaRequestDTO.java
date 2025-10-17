package com.codcozapipostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarefaRequestDTO {
    private Long tarefaId;
    private Long empresaId;
    private Long tipoTarefaId;
    private Long ingredienteId;
    private Long relatorId;
    private Long responsavelId;
    private Long pedidoId;
    private String situacao;
    private LocalDate dataCriacao;
    private LocalDate dataTarefa;
    private LocalDate dataLimite;
    private LocalDate dataConclusao;
}
