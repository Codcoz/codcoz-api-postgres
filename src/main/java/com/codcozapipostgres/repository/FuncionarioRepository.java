package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query(value = "SELECT * FROM Funcionario WHERE email = :email",nativeQuery = true)
    Funcionario buscaPorEmail(@Param("email") String email);
    @Query(value = "SELECT * FROM Funcionario WHERE id = :id",nativeQuery = true)
    Funcionario buscaPorId(@Param("id") Long id);
    @Query(value = "SELECT * FROM Funcionario WHERE empresa_id = :empresaId",nativeQuery = true)
    List<Funcionario> buscaPorEmpresaId(@Param("empresaId") Integer empresaId);
}
