package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginDTO {
    
    @NotBlank
    String cpf;

    @NotBlank
    String senha;
}
