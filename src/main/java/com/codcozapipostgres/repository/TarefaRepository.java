package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Tarefa;
import com.codcozapipostgres.projection.TarefaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query(value = "SELECT * FROM func_busca_tarefa_data(:data, :email)", nativeQuery = true)
    List<TarefaProjection> buscaPorData(@Param("data") LocalDate data,
                                        @Param("email") String email);

    @Query(value = "SELECT * FROM func_lista_tarefa_periodo(:dataInicio, :dataFim, :email)", nativeQuery = true)
    List<TarefaProjection> buscaPorPeriodo(@Param("dataInicio") LocalDate dataInicio,
                                           @Param("dataFim") LocalDate dataFim,
                                           @Param("email") String email);

    @Query(value = "SELECT * FROM func_lista_tarefa_periodo(:dataInicio, :dataFim, :email) WHERE tipo_tarefa = :tipo", nativeQuery = true)
    List<TarefaProjection> buscaPorPeriodoETipo(@Param("dataInicio") LocalDate dataInicio,
                                                @Param("dataFim") LocalDate dataFim,
                                                @Param("email") String email,
                                                @Param("tipo") String tipo);

    @Query(value = "SELECT * FROM func_lista_tarefa_data_conclusao(:dataConclusao, CAST(:empresaId AS integer))", nativeQuery = true)
    List<TarefaProjection> buscaTarefaPorConclusao(@Param("dataConclusao") LocalDate dataConclusao,
                                                   @Param("empresaId") Long empresaId);

    @Query(value = "SELECT * FROM func_lista_tarefa(:empresaId)", nativeQuery = true)
    List<TarefaProjection> listaTarefas(@Param("empresaId") Integer empresaId);

    @Procedure(procedureName = "sp_conclui_tarefa")
    void finalizaTarefa(Integer idn);

    @Procedure(procedureName = "public.sp_conclui_tarefa")
    void finalizaAuditoria(
            @Param("idn") Integer idn,
            @Param("contagemn") Integer contagemn
    );
}