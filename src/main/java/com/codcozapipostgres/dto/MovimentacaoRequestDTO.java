package com.codcozapipostgres.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimentacaoRequestDTO {
    private Integer produtoId;
    private Integer empresaId;
    private Integer tipoMovimentacaoId;
    private LocalDateTime data;
    private Integer quantidadeInicial;
    private Integer quantidadeFinal;
    private Integer diferenca;
}
