package com.codcozapipostgres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "movimentacao")
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "produto_id")
    private Integer produtoId;
    @Column(name = "tipo_movimentacao_id")
    private Integer tipoMovimentacaoId;
    private LocalDateTime data;
    @Column(name = "old_quantidade")
    private Integer quantidadeInicial;
    @Column(name = "new_quantidade")
    private Integer quantidadeFinal;
    private Integer diferenca;
}
