package com.db.crudpessoabackend.domain.usuario.pessoa.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path="pessoas")
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);

    @Query("SELECT p FROM Pessoa p WHERE p.contato.email = :email")
    Optional<Pessoa> findByEmail(String email);

    @Query("SELECT p FROM Pessoa p WHERE p.contato.celular = :celular")
    Optional<Pessoa> findByCelular(String celular);

    @Query("SELECT p FROM Pessoa p JOIN p.enderecos e WHERE e.cidade = :cidade")
    List<Pessoa> findByCidade(@Param("cidade") String cidade);

    @Query("SELECT p FROM Pessoa p JOIN p.enderecos e WHERE e.rua = :rua")
    List<Pessoa> findByRua(@Param("rua") String rua);

    @Query("SELECT p FROM Pessoa p JOIN p.enderecos e WHERE e.bairro = :bairro")
    List<Pessoa> findByBairro(@Param("bairro") String bairro);

    @Query("SELECT p FROM Pessoa p JOIN p.enderecos e WHERE e.cep = :cep")
    List<Pessoa> findByCep(@Param("cep") String cep);

    @Query("SELECT p FROM Pessoa p JOIN p.enderecos e WHERE e.estado = :estado")
    List<Pessoa> findByEstado(@Param("estado") Estado estado);
}
