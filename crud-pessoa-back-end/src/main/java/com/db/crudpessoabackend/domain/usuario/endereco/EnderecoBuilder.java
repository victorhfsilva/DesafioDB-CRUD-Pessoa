package com.db.crudpessoabackend.domain.usuario.endereco;

import com.db.crudpessoabackend.domain.base.BaseEntityAuditBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public class EnderecoBuilder extends BaseEntityAuditBuilder implements IEnderecoBuilder {

    private String numero;
    private String complemento;
    private String rua;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;
    private Pessoa pessoa;

    @Override
    public IEnderecoBuilder numero(String numero) {
        this.numero = numero;
        return this;
    }

    @Override
    public IEnderecoBuilder complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    @Override
    public IEnderecoBuilder rua(String rua) {
        this.rua = rua;
        return this;
    }

    @Override
    public IEnderecoBuilder bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    @Override
    public IEnderecoBuilder cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    @Override
    public IEnderecoBuilder estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public IEnderecoBuilder cep(String cep) {
        this.cep = cep;
        return this;
    }

    @Override
    public IEnderecoBuilder pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public Endereco build() {
        return new Endereco(
            getCreatedBy(), 
            getUpdatedBy(), 
            getDeactivatedBy(), 
            getCreatedAt(), 
            getUpdatedAt(), 
            getDeactivatedAt(), 
            numero, 
            complemento, 
            rua, 
            bairro, 
            cidade, 
            estado, 
            cep,
            pessoa);
    }

    @Override
    public IEnderecoBuilder reset() {
        return new EnderecoBuilder();
    }
}

