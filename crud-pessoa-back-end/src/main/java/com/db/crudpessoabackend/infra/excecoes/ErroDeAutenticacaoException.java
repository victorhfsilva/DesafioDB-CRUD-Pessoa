package com.db.crudpessoabackend.infra.excecoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErroDeAutenticacaoException extends RuntimeException {
    private String mensagem;
    private String mensageDeExcecao;

    public ErroDeAutenticacaoException(String mensagem) {
        this.mensagem = mensagem;
    }
}
