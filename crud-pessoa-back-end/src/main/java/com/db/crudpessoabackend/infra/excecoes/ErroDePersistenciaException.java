package com.db.crudpessoabackend.infra.excecoes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErroDePersistenciaException extends RuntimeException {
    private String mensagem;
    private String mensageDeExcecao;

    public ErroDePersistenciaException(String mensagem) {
        this.mensagem = mensagem;
    }
}
