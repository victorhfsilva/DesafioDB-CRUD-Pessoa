package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IPessoaService {
    Pessoa buscarPorCpf(String cpf);
    Pessoa registrar(Pessoa pessoa, Pessoa editor);
    Pessoa atualizar(String cpf, Pessoa novaPessoa, Pessoa editor);
    Pessoa excluir(String cpf);
    Pessoa desativar(String cpf, Pessoa editor);
    Pessoa ativar(String cpf, Pessoa editor);
    Integer mostrarIdade(String cpf);
}
