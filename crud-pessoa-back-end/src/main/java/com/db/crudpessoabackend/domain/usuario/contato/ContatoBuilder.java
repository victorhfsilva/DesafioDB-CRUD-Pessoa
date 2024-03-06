package com.db.crudpessoabackend.domain.usuario.contato;

import com.db.crudpessoabackend.domain.base.BaseEntityAuditBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.interfaces.IContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;


public class ContatoBuilder  extends BaseEntityAuditBuilder implements IContatoBuilder{

    private String email;
    private String celular;
    private Pessoa pessoa;

    @Override
    public IContatoBuilder email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public IContatoBuilder celular(String celular) {
        this.celular = celular;
        return this;
    }

    @Override
    public IContatoBuilder pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public Contato build() {
        return new Contato(
        getCreatedBy(), 
        getUpdatedBy(), 
        getDeactivatedBy(), 
        getCreatedAt(), 
        getUpdatedAt(), 
        getDeactivatedAt(), 
        email, 
        celular,
        pessoa);
    }

    @Override
    public IContatoBuilder reset() {
        return new ContatoBuilder();
    }

}
