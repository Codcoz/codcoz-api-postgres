package com.codcozapipostgres.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoRequestDTO {
    @NotBlank
    @Size(min = 8,max = 13)
    private String codigoEan;
    @NotNull
    @Min(0)
    private Integer empresaId;
    @NotNull
    @Min(0)
    private Integer ingredienteId;
    @NotNull
    @Min(0)
    private Integer unidadeMedidaId;
    @NotBlank
    private String nome;
    @NotNull
    @Min(0)
    private Integer quantidade;
    @NotBlank
    private String descricao;
    @NotBlank
    private String marca;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validade;
}
