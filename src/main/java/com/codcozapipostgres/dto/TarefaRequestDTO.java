package com.codcozapipostgres.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Min(0)
    private Integer id;
    @NotNull
    @Min(0)
    private Long empresaId;
    @NotNull
    @Min(0)
    private Long tipoTarefaId;
    @NotNull
    @Min(0)
    private Long ingredienteId;
    @NotNull
    @Min(0)
    private Long relatorId;
    @NotNull
    @Min(0)
    private Long responsavelId;
    @NotNull
    @Min(0)
    private Long pedidoId;
    @NotBlank
    private String situacao;
    @NotBlank
    private LocalDate dataCriacao;
    @NotBlank
    private LocalDate dataTarefa;
    @NotBlank
    private LocalDate dataLimite;
    @NotBlank
    private LocalDate dataConclusao;
}
