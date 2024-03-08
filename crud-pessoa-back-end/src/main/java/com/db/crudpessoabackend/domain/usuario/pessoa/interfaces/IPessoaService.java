package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IPessoaService {
    Pessoa registrar(Pessoa pessoa);
    Pessoa atualizar(String cpf, Pessoa novaPessoa);
    Pessoa excluir(String cpf);
    Integer mostrarIdade(String cpf);
}
