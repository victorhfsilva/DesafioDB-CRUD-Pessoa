package com.db.crudpessoabackend.domain.usuario.contato.dtos;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContatoRespostaDTO {

    private Long id;
    private String email;
    private String celular;

    public ContatoRespostaDTO(Contato contato) {
        this.id = contato.getId();
        this.email = contato.getEmail();
        this.celular = contato.getCelular();
    }
}
