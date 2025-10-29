package com.codcozapipostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaRequestDTO {
    private String cnpj;
    private String nome;
    private String sigla;
    private String email;
    private String status;
    private String capacidadeEstoque;
}
