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
public class TarefaResponseDTO {
    private String empresa;
    private String tipoTarefa;
    private String ingrediente;
    private String relator;
    private String responsavel;
    private String pedido;
    private String situacao;
    private LocalDate dataCriacao;
    private LocalDate dataLimite;
    private LocalDate dataConclusao;
}
