package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.base.interfaces.IEntidadeBaseAudicaoBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import java.time.LocalDate;
import java.util.List;

public interface IPessoaBuilder extends IEntidadeBaseAudicaoBuilder {
    IPessoaBuilder nome(String nome);
    IPessoaBuilder sobrenome(String sobrenome);
    IPessoaBuilder cpf(String cpf);
    IPessoaBuilder senha(String senha);
    IPessoaBuilder papel(Papel papel);
    IPessoaBuilder dataDeNascimento(LocalDate dataNascimento);
    IPessoaBuilder contato(Contato contato);
    IPessoaBuilder enderecos(List<Endereco> enderecos);
    Pessoa build();
    IPessoaBuilder reset();
}
