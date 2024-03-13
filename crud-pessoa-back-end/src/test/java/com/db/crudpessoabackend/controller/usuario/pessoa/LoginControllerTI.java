package com.db.crudpessoabackend.controller.usuario.pessoa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.LoginDTO;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LoginControllerTI {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired TokenService tokenService;

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/reiniciar-ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados-admin.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadoUmUsuarioAdministrador_quandoLogado_DeveRetornaTokenValido(){
        LoginDTO loginDTO = LoginDTO.builder()
                                    .cpf("admin")
                                    .senha("admin")
                                    .build();

        HttpEntity<LoginDTO> requisicao = new HttpEntity<>(loginDTO);

        ResponseEntity<String> resposta = restTemplate.postForEntity("http://localhost:" + port + "/login", requisicao, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertTrue(tokenService.isTokenValido(resposta.getBody()));
    }

}
