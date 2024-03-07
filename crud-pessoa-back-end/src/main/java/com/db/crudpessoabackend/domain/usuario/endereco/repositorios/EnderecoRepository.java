package com.db.crudpessoabackend.domain.usuario.endereco.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;

@RepositoryRestResource(path = "enderecos")
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
