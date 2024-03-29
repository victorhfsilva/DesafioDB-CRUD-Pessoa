package com.db.crudpessoabackend.infra.excecoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EntidadeNaoEncontradaException extends RuntimeException {
    private String mensagem;
    private String mensageDeExcecao;

    public EntidadeNaoEncontradaException(String mensagem) {
        this.mensagem = mensagem;
    }
}
