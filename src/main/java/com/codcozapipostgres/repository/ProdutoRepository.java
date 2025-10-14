package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}
