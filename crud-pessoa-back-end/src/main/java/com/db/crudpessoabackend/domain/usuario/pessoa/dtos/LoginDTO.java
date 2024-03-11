package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginDTO {
    
    @NotBlank
    @CPF
    String cpf;

    @NotBlank
    String password;
}
