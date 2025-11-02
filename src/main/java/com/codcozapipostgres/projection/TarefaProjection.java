package com.codcozapipostgres.projection;

import java.time.LocalDate;

public interface TarefaProjection {
    Integer getId();
    String getEmpresa();
    String getTipoTarefa();
    String getIngrediente();
    String getRelator();
    String getResponsavel();
    String getPedido();
    String getSituacao();
    Double getQuantidadeEsperada();
    Double getContagem();
    LocalDate getDataCriacao();
    LocalDate getDataLimite();
    LocalDate getDataConclusao();
}
