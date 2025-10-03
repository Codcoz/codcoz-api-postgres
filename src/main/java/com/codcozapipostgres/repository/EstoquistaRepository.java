package com.codcozapipostgres.repository;

import com.codcozapipostgres.model.Estoquista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoquistaRepository extends JpaRepository<Estoquista, Long> {

}
