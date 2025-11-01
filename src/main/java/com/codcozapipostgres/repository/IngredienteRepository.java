package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    @Query(value = "SELECT * FROM ingrediente WHERE empresa_id = :empresaId",nativeQuery = true)
    List<Ingrediente> listaPorEmpresa(@Param("empresaId") Long empresaId);
}
