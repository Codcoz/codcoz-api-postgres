package com.codcozapipostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IngredienteRequestDTO {
    private Integer categoriaIngrediente;
    private String nome;
    private String descricao;
    private Integer quantidadeMinima;
    private Long empresaId;
}
