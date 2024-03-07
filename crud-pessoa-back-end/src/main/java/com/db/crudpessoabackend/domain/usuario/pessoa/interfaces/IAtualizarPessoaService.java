package com.db.crudpessoabackend.domain.usuario.pessoa.interfaces;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IAtualizarPessoaService {
    Pessoa atualizar(String cpf, Pessoa novaPessoa);
}
