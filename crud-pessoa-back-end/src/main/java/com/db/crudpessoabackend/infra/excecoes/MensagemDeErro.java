package com.db.crudpessoabackend.infra.excecoes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MensagemDeErro {
    private String mensagem;
    private String mensagemDaExcecao;
}
