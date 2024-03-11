package com.db.crudpessoabackend.domain.usuario.contato;

import com.db.crudpessoabackend.domain.usuario.contato.interfaces.IContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public class ContatoBuilder implements IContatoBuilder{

    private String email;
    private String celular;
    private Pessoa pessoa;

    @Override
    public ContatoBuilder email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public ContatoBuilder celular(String celular) {
        this.celular = celular;
        return this;
    }

    @Override
    public ContatoBuilder pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public Contato build() {
        return new Contato(
        email, 
        celular,
        pessoa);
    }

    @Override
    public ContatoBuilder reset() {
        return new ContatoBuilder();
    }

}
