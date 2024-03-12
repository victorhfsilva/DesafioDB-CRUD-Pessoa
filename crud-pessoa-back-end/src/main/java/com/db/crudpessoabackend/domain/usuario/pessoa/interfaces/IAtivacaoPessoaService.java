package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IAtivacaoPessoaService {
    Pessoa desativar(String cpf, Pessoa editor);
    Pessoa ativar(String cpf, Pessoa editor);
}
