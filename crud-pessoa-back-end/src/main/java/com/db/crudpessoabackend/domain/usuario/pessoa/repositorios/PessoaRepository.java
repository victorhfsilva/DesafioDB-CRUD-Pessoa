package com.db.crudpessoabackend.domain.usuario.pessoa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

import java.util.Optional;

@RepositoryRestResource(path="pessoas")
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
}
