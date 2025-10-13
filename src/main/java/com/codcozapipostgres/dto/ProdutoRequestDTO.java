package com.codcozapipostgres.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ProdutoRequestDTO {
    private String codigoEan;
    private Integer empresaId;
    private Integer ingredienteId;
    private Integer unidadeMedidaId;
    private String nome;
    private Integer quantidade;
    private String descricao;
    private String marca;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validade;
}
