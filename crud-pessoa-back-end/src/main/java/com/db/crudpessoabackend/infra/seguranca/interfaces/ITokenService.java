package com.db.crudpessoabackend.infra.seguranca.interfaces;

public interface ITokenService {
    String gerarToken(String cpf);
    boolean isTokenValido(String token);
    String obterSujeito(String token);
}
