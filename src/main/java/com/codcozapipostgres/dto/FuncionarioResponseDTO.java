package com.codcozapipostgres.dto;

import java.time.LocalDate;

public class FuncionarioResponseDTO {

    private String email;
    private String nome;
    private String sobrenome;
    private Long empresaId;
    private LocalDate dataContratacao;
    private String status;

    public FuncionarioResponseDTO() {}
    public FuncionarioResponseDTO(String email, String nome, String sobrenome, Long empresaId, LocalDate dataContratacao, String status) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.empresaId = empresaId;
        this.dataContratacao = dataContratacao;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public Long getEmpresaId() {
        return empresaId;
    }
    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
    public LocalDate getDataContratacao() {
        return dataContratacao;
    }
    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
