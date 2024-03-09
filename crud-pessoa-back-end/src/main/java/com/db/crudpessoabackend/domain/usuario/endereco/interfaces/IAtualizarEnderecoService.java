package com.db.crudpessoabackend.domain.usuario.endereco.interfaces;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;

public interface IAtualizarEnderecoService {
    Endereco atualizar(Long id, Endereco novoEndereco);
}
