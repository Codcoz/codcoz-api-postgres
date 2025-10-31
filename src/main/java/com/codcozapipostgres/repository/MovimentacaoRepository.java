package com.codcozapipostgres.repository;

import com.codcozapipostgres.dto.MovimentacaoResponseDTO;
import com.codcozapipostgres.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query(value = "SELECT * FROM movimentacao WHERE tipo_movimentacao_id = 1 AND empresa_id = :idEmpresa", nativeQuery = true)
    List<Movimentacao> listarEntradasPorEmpresa(@Param("idEmpresa") Long idEmpresa);

    @Query(value = "SELECT * FROM movimentacao WHERE tipo_movimentacao_id = 2 AND empresa_id = :idEmpresa", nativeQuery = true)
    List<Movimentacao> listarBaixasPorEmpresa(@Param("idEmpresa") Long idEmpresa);

    @Query(value = "SELECT * FROM movimentacao WHERE tipo_movimentacao_id = 1 AND empresa_id = :idEmpresa AND data BETWEEN :dataInicio AND :dataFim", nativeQuery = true)
    List<Movimentacao> listarEntradasPorEmpresaPorPeriodo(
            @Param("idEmpresa") Long idEmpresa,
            @Param("dataInicio") String dataInicio,
            @Param("dataFim") String dataFim);

    @Query(value = "SELECT * FROM movimentacao WHERE tipo_movimentacao_id = 2 AND empresa_id = :idEmpresa AND data BETWEEN :dataInicio AND :dataFim", nativeQuery = true)
    List<Movimentacao> listarBaixasPorEmpresaPorPeriodo(
            @Param("idEmpresa") Long idEmpresa,
            @Param("dataInicio") String dataInicio,
            @Param("dataFim") String dataFim);
}

