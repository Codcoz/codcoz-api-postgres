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
public class ProdutoResponseDTO {
    private Long id;
    private String codigoEan;
    private String nome;
    private String descricao;
    private Integer quantidade;
    private String marca;
    private LocalDate validade;
}
