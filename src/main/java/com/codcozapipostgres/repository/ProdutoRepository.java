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
    Integer contarProdutosProximosValidade(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT * FROM func_conta_produto_baixo_estoque(:idEmpresa)", nativeQuery = true)
    Integer contarProdutosBaixoEstoque(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT * FROM func_conta_produto(:idEmpresa)",nativeQuery = true)
    Integer contarProdutosEmEstoque(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT p.* FROM produto p " +
            "JOIN ingrediente i ON p.ingrediente_id = i.id " +
            "WHERE p.quantidade < i.quantidade_minima " +
            "AND p.empresa_id = (:idEmpresa)", nativeQuery = true)
    List<Produto> listarEstoqueBaixo(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT * FROM produto p " +
            "WHERE p.validade - CURRENT_DATE <= 14 " +
            "AND p.validade >= CURRENT_DATE " +
            "AND p.empresa_id = (:idEmpresa)",nativeQuery = true)
    List<Produto> listarProximoValidade(@Param("idEmpresa") Integer idEmpresa);

    @Query(value = "SELECT * FROM produto p WHERE p.empresa_id = (:id)",nativeQuery = true)
    List<Produto> listarEstoque(@Param("id") Integer idEmpresa);

    @Query(value = "SELECT * FROM produto p WHERE p.codigo_ean = :codigoEan", nativeQuery = true)
    Produto buscarProdutoPorCodigoEan(@Param("codigoEan") String codigoEan);

    @Procedure(procedureName = "public.sp_movimenta_produtos")
    void movimentaProdutos(@Param("idProduto") String codigoEan, @Param("quantidade") Integer quantidade);
}
