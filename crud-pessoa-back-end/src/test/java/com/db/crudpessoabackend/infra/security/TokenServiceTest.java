package com.db.crudpessoabackend.infra.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.BuscarPessoaPorCpf;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock(lenient = true)
    private BuscarPessoaPorCpf buscarPessoaPorCpf;

    private PessoaBuilder pessoaBuilder;

    private static String jwtSecretValido = "secret";

    private static String cpfValido = "123.456.789-99";

    private static String issuerValido = "DB";

    @BeforeEach
    void configurar() {
        pessoaBuilder = new PessoaBuilder();
    }

    @ParameterizedTest
    @MethodSource("parametros")
    void dadoAlgunsUsuarios_quandoValidados_DeveRetornarSeOTokenEhValidoOuNao(String cpf, 
                                                                                        String issuer, 
                                                                                        String jwtSecret,
                                                                                        boolean resultadoEsperado) {
        Algorithm algoritmo = Algorithm.HMAC256(jwtSecret);
        
        String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(cpf)
                .withExpiresAt(LocalDateTime.now().plusHours(1L).toInstant(ZoneOffset.of("-03:00")))
                .sign(algoritmo);

        Pessoa pessoa = pessoaBuilder.cpf(cpfValido).nome("Adalberto").build();
        
        when(buscarPessoaPorCpf.buscarPorCpf(cpfValido)).thenReturn(pessoa);

        boolean resultadoObtido = tokenService.isTokenValido(token);
        
        assertEquals(resultadoEsperado, resultadoObtido);

    }

    private static Stream<Arguments> parametros() {
        return Stream.of(
            Arguments.of(cpfValido, issuerValido, jwtSecretValido, true),
            Arguments.of(cpfValido, "issuer Inválido", jwtSecretValido, false),
            Arguments.of(cpfValido, issuerValido, "jwtSecret Inválido", false),
            Arguments.of("cpf Inválido", issuerValido, jwtSecretValido, false)
        );
    }
    
}
