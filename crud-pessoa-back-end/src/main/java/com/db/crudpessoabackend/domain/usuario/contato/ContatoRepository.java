package com.db.crudpessoabackend.domain.usuario.contato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "contatos")
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
}
