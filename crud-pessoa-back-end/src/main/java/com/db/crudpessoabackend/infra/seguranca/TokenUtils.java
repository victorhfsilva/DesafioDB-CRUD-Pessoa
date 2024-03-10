package com.db.crudpessoabackend.infra.seguranca;

public class TokenUtils {

    public static String extractToken(String authorizationHeader) {
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            return "";
        }
    }
    
}
