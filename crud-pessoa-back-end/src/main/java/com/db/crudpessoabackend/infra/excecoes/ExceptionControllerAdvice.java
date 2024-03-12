package com.db.crudpessoabackend.infra.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(ErroDeAutenticacaoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MensagemDeErro> handleErroDeAutenticacao(ErroDeAutenticacaoException ex) {
        
        MensagemDeErro resposta = MensagemDeErro.builder()
                                                .mensagem(ex.getMensagem())
                                                .mensagemDaExcecao(ex.getMensageDeExcecao())
                                                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resposta);
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MensagemDeErro> handleTokenExpirado(TokenExpiredException ex) {
        
        MensagemDeErro resposta = MensagemDeErro.builder()
                                                .mensagem("O token expirou.")
                                                .mensagemDaExcecao(ex.getMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resposta);
    }


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MensagemDeErro> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        
        MensagemDeErro resposta = MensagemDeErro.builder()
                                                .mensagem(ex.getMensagem())
                                                .mensagemDaExcecao(ex.getMensageDeExcecao())
                                                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(ErroDePersistenciaException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MensagemDeErro> handleErroDePersistenciaDosDados(ErroDePersistenciaException ex) {
        
        MensagemDeErro resposta = MensagemDeErro.builder()
                                                .mensagem(ex.getMensagem())
                                                .mensagemDaExcecao(ex.getMensageDeExcecao())
                                                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MensagemDeErro> handleExcecaoGenerica(Exception ex) {
        
        MensagemDeErro resposta = MensagemDeErro.builder()
                                                .mensagem("Internal Server Error")
                                                .mensagemDaExcecao(ex.getMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
