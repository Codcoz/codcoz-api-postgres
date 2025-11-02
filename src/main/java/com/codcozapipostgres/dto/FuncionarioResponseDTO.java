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
public class FuncionarioResponseDTO {
    private Long id;
    private String email;
    private String nome;
    private String sobrenome;
    private Long empresaId;
    private LocalDate dataContratacao;
    private String status;
}
