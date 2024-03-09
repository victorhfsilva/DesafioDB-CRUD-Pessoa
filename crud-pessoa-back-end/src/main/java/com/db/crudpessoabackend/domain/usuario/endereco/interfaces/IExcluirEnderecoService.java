package com.db.crudpessoabackend.domain.usuario.endereco.interfaces;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;

public interface IExcluirEnderecoService {
    Endereco excluir(Long id);
}
