package com.db.crudpessoabackend.domain.usuario.endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "enderecos")
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
