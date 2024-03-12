package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.AtivacaoPessoaService;

@SpringBootTest
@ActiveProfiles("test")
class AtivacaoPessoaServiceTI {

    @Autowired
    private AtivacaoPessoaService ativacaoService;

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoDesativada_DeveRetornarAEntidadeDesativada(){
        Pessoa editor = pessoaRepository.findById(1L).orElseThrow();
        ativacaoService.desativar("198.654.156-11", editor);
        
        assertFalse(pessoaRepository.findByCpf("198.654.156-11").get().isActive());
        
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoAtivada_DeveRetornarAEntidadeAtivada(){
        Pessoa editor = pessoaRepository.findById(1L).orElseThrow();
        
        ativacaoService.desativar("198.654.156-11", editor);
        ativacaoService.ativar("198.654.156-11", editor);
        
        assertTrue(pessoaRepository.findByCpf("198.654.156-11").get().isActive());
        
    }
}
