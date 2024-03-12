package com.db.crudpessoabackend.domain.usuario.endereco.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;

@RepositoryRestResource(path = "enderecos")
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
    @Query("SELECT e FROM Endereco e JOIN e.pessoa p WHERE p.cpf = :cpf")
    List<Endereco> findByCpf(@Param("cpf") String cpf);
}
