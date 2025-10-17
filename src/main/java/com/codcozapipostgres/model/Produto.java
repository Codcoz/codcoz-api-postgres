package com.codcozapipostgres.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_ean")
    private String codigoEan;
    @Column(name="empresa_id")
    private Integer empresaId;
    @Column(name = "ingrediente_id")
    private Integer ingredienteId;
    @Column(name = "unidade_medida_id")
    private Integer unidadeMedidaId;
    private String nome;
    private Integer quantidade;
    private String descricao;
    private String marca;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validade;

}
