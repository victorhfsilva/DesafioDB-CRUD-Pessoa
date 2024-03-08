package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IExcluirPessoaService {
    Pessoa excluir(String cpf);
}
