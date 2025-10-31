package com.codcozapipostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimentacaoResponseDTO {
    private Long id;
    private Integer produtoId;
    private Integer tipoMovimentacaoId;
    private LocalDateTime data;
    private Integer quantidadeInicial;
    private Integer quantidadeFinal;
    private Integer diferenca;
}
