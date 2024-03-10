package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RespostaRegistrarDTO {
    
    String token;
    PessoaRespostaDTO respostaPessoa;
}
