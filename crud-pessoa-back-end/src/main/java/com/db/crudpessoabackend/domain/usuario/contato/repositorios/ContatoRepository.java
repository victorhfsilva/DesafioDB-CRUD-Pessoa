package com.db.crudpessoabackend.domain.usuario.contato.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;

@RepositoryRestResource(path = "contatos")
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
    @RestResource(exported = false) 
    @Override
    <S extends Contato> S save(S entity);

    @RestResource(exported = false) 
    @Override
    void deleteById(Long id);

    @RestResource(exported = false) 
    @Override
    void delete(Contato entity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends Contato> entities);

    @RestResource(exported = false)
    @Override
    void deleteAll();

    @Query("SELECT c FROM Contato c WHERE c.pessoa.cpf = :cpf")
    Contato findByCpf(@Param("cpf") String cpf);
}
