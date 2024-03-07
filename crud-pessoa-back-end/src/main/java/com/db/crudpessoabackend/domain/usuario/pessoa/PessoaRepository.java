package com.db.crudpessoabackend.domain.usuario.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="pessoas")
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
