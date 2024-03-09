package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.ExcluirPessoaService;

@SpringBootTest
@ActiveProfiles("test")
class ExcluirPessoaServiceTI {
    
    @Autowired
    private ExcluirPessoaService excluirService;

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoExcluida_NaoDeveEncontrarEntidadeNoBancoDeDados(){
        
        excluirService.excluir("198.654.156-11");
        
        assertFalse(pessoaRepository.findByCpf("198.654.156-11").isPresent());
        
    }
}
