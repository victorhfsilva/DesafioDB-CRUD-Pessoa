package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IBuscarPessoaPorCpf {
    Pessoa buscarPorCpf(String cpf);
}
