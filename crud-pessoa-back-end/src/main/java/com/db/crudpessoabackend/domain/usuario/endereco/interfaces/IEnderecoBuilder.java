package com.db.crudpessoabackend.domain.usuario.endereco.interfaces;

import com.db.crudpessoabackend.domain.base.interfaces.IBaseEntityAuditBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IEnderecoBuilder extends IBaseEntityAuditBuilder {
    IEnderecoBuilder numero(String numero);
    IEnderecoBuilder complemento(String complemento);
    IEnderecoBuilder rua(String rua);
    IEnderecoBuilder bairro(String bairro);
    IEnderecoBuilder cidade(String cidade);
    IEnderecoBuilder estado(Estado estado);
    IEnderecoBuilder cep(String cep);
    IEnderecoBuilder pessoa(Pessoa pessoa);
    Endereco build();
    IEnderecoBuilder reset();
}

