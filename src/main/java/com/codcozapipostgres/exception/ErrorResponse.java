package com.codcozapipostgres.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String erro;
    private String descricao;
    private Integer status;
}
