package com.db.crudpessoabackend.infra.seguranca.servicos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;

@Service
public class TokenService implements ITokenService {

    /*
     * Dúvida:
     * Tentei utilizar @Value para injetar o segredo através de uma variável de ambiente,
     * porém meus testes não passaram.
     */
    private String jwtSecret = "secret";

    private String issuer = "DB";

    private PessoaRepository pessoaRepository;

    Algorithm algoritmo = Algorithm.HMAC256(jwtSecret);

    public TokenService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
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
            Pessoa pessoa = pessoaRepository.findByCpf(cpf)
                                            .orElseThrow(() ->
                                            new EntidadeNaoEncontradaException(
                                                "Não foi possível encontrar a pessoa com CPF " + cpf));
            return pessoa != null;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }
}
