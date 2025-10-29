package com.codcozapipostgres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "categoria_ingrediente_id")
    private Integer categoriaIngrediente;
    private String nome;
    private String descricao;
    @Column(name = "quantidade_minima")
    private Integer quantidadeMinima;
    @Column(name = "empresa_id")
    private Long empresaId;
}
