package com.db.crudpessoabackend.infra.seguranca.utils;

import org.springframework.stereotype.Component;

import com.db.crudpessoabackend.infra.excecoes.ErroDeAutenticacaoException;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenUtils {

    private ITokenService tokenService;
    
    public String extrairToken(String authorizationHeader) {
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            return "";
        }
    }
    
    public String validarToken(String headerAutorizacao){
        String token = extrairToken(headerAutorizacao);
        
        if (!tokenService.isTokenValido(token)){
            throw new ErroDeAutenticacaoException("Token Inválido.");
        }

        return token;
    }
}
