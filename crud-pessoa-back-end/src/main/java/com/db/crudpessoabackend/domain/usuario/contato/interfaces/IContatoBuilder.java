package com.db.crudpessoabackend.domain.usuario.contato.interfaces;

import com.db.crudpessoabackend.domain.base.interfaces.IBaseEntityAuditBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

public interface IContatoBuilder extends IBaseEntityAuditBuilder {
    IContatoBuilder email(String email);
    IContatoBuilder celular(String celular);
    IContatoBuilder pessoa(Pessoa pessoa);
    Contato build(); 
    IContatoBuilder reset();
}
