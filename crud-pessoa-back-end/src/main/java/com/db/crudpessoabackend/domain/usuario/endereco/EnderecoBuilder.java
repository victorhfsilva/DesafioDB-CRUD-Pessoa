package com.db.crudpessoabackend.domain.usuario.endereco;

import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public class EnderecoBuilder implements IEnderecoBuilder {

    private String numero;
    private String complemento;
    private String rua;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;
    private Pessoa pessoa;

    @Override
    public EnderecoBuilder numero(String numero) {
        this.numero = numero;
        return this;
    }

    @Override
    public EnderecoBuilder complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    @Override
    public EnderecoBuilder rua(String rua) {
        this.rua = rua;
        return this;
    }

    @Override
    public EnderecoBuilder bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    @Override
    public EnderecoBuilder cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    @Override
    public EnderecoBuilder estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public EnderecoBuilder cep(String cep) {
        this.cep = cep;
        return this;
    }

    @Override
    public EnderecoBuilder pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public Endereco build() {
        return new Endereco(
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
    public EnderecoBuilder reset() {
        return new EnderecoBuilder();
    }
}

