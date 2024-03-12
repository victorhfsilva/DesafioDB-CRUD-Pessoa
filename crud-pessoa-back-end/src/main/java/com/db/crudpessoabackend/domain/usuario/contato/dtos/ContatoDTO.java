package com.db.crudpessoabackend.domain.usuario.contato.dtos;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ContatoDTO {

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "\\d+", message = "A string deve conter apenas dígitos.")
    private String celular;

    public Contato converterParaEntidade(){
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        return contatoBuilder.email(email)
                        .celular(celular)
                        .build();
    }
}
