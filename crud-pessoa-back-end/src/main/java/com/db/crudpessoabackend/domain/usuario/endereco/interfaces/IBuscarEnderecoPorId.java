package com.db.crudpessoabackend.domain.usuario.endereco.interfaces;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;

public interface IBuscarEnderecoPorId {
    Endereco buscarPorId(Long id);
}
