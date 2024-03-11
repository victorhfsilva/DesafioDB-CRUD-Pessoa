package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IAtivacaoPessoaService {
    Pessoa desativar(String cpf);
    Pessoa ativar(String cpf);
}
