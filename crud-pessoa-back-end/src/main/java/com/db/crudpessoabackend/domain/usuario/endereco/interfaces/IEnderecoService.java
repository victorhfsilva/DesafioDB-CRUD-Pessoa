package com.db.crudpessoabackend.domain.usuario.endereco.interfaces;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;

public interface IEnderecoService {
    Endereco buscarEnderecoPorId(Long id);
    Endereco adicionar(Endereco endereco);
    Endereco atualizar(Long id, Endereco endereco);
    Endereco excluir(Long id);
}
