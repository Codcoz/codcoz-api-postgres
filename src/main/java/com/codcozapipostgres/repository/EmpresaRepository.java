package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCnpj(String cnpj);

    @Query(value = "SELECT * FROM func_calcula_ocupacao_estoque(:id_empresa)",nativeQuery = true)
    Double calculaPorcentagemOcupacao(@Param("id_empresa")  Integer idEmpresa);
}
