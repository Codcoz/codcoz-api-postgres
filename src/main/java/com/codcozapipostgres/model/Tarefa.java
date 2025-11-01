package com.codcozapipostgres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "empresa_id")
    private Long empresaId;
    @Column(name="tipo_tarefa_id")
    private Long tipoTarefaId;
    @Column(name = "ingrediente_id")
    private Long ingredienteId;
    @Column(name="relator_id")
    private Long relatorId;
    @Column(name = "responsavel_id")
    private Long responsavelId;
    @Column(name="pedido_id")
    private Long pedidoId;
    private String situacao;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "data_limite")
    private LocalDate dataLimite;
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
}
