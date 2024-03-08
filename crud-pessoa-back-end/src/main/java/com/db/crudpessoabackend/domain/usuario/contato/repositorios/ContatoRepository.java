package com.db.crudpessoabackend.domain.usuario.contato.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;

@RepositoryRestResource(path = "contatos")
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
    @Query("SELECT c FROM Contato c WHERE c.pessoa.cpf = :cpf")
    Contato findByCpf(@Param("cpf") String cpf);
}
