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
public class FuncionarioRequestDTO {
    @NotNull
    @Min(0)
    private Long empresaId;
    @NotNull
    @Min(0)
    private Long funcaoId;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @Size(min = 5,max = 7)
    private String status;
    @NotNull
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataContratacao;
}
