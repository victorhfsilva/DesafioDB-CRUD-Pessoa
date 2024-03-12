package com.db.crudpessoabackend.infra.seguranca.servicos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.BuscarPessoaPorCpf;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;

@Service
public class TokenService implements ITokenService {

    private String jwtSecret = "secret";
    private String issuer = "DB";
    private Algorithm algoritmo = Algorithm.HMAC256(jwtSecret);
    private BuscarPessoaPorCpf buscarPessoaPorCpf;

    public TokenService(BuscarPessoaPorCpf buscarPessoaPorCpf) {
        this.buscarPessoaPorCpf = buscarPessoaPorCpf;
    }

    public String gerarToken(String cpf){

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(cpf)
                .withExpiresAt(LocalDateTime.now().plusHours(1L).toInstant(ZoneOffset.of("-03:00")))
                .sign(algoritmo);
    }

    public boolean isTokenValido(String token){
        try {
            JWT.require(algoritmo)
                .withIssuer(issuer)
                .build()
                .verify(token);
            String cpf = JWT.decode(token).getSubject();
            Pessoa pessoa = buscarPessoaPorCpf.buscarPorCpf(cpf);
            return pessoa != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public String obterSujeito(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }
}
