package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    @Query(value = "SELECT * FROM func_conta_produto_validade(:idEmpresa)", nativeQuery = true)
    Integer contaProximosValidade(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT * FROM func_conta_produto_baixo_estoque(:idEmpresa)", nativeQuery = true)
    Integer contaBaixoEstoque(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT * FROM func_conta_produto(:idEmpresa)",nativeQuery = true)
    Integer contaEstoque(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT p.* FROM produto p " +
            "JOIN ingrediente i ON p.ingrediente_id = i.id " +
            "WHERE p.quantidade < i.quantidade_minima " +
            "AND p.empresa_id = (:idEmpresa)", nativeQuery = true)
    List<Produto> listaEstoqueBaixo(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT p.* FROM produto p " +
            "WHERE p.validade - CURRENT_DATE <= 14 " +
            "AND p.validade >= CURRENT_DATE " +
            "AND p.empresa_id = (:idEmpresa)",nativeQuery = true)
    List<Produto> listaProximoValidade(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT p.* FROM produto p WHERE p.empresa_id = (:id)",nativeQuery = true)
    List<Produto> listaEstoque(@Param("id") Integer idEmpresa);

    @Query(value = "SELECT p.* FROM produto p WHERE p.codigo_ean = :codigoEan", nativeQuery = true)
    Produto buscaPorCodigoEan(@Param("codigoEan") String codigoEan);

    @Procedure(procedureName = "public.sp_movimenta_produtos")
    void movimentaProdutos(@Param("codigo_eann") String codigoEan, @Param("quantidaden") Integer quantidade);
}