package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;

@SpringBootTest
@ActiveProfiles("test")
class ExcluirEnderecoServiceTI {
        
    @Autowired
    private ExcluirEnderecoService excluirService;

    @Autowired
    private EnderecoRepository enderecoRepository;

        @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmEnderecoValidoSalvoNoBancoDeDados_QuandoExcluido_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        excluirService.excluir(1L);
        
        assertFalse(enderecoRepository.findById(1L).isPresent());
        
    }
}
