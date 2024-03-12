package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IRegistrarPessoaService {
    Pessoa registrar(Pessoa pessoa, Pessoa editor);
}
