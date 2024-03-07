package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.AtualizarPessoaService;

@SpringBootTest
@ActiveProfiles("test")
class AtualizarPessoaTI {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AtualizarPessoaService atualizarPessoaService;

    private ContatoBuilder contatoBuilder;
    
    private EnderecoBuilder enderecoBuilder;

    private PessoaBuilder pessoaBuilder;
    
    @BeforeEach 
    private void configurar() {
        contatoBuilder = new ContatoBuilder();
        enderecoBuilder = new EnderecoBuilder();
        pessoaBuilder = new PessoaBuilder();
    }

    @Test
    @SqlGroup({
        @Sql("/db/limpar.sql"),
        @Sql("/db/dados.sql")
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoAtualizada_DeveRetornarPessoaCorretaPorId(){
        assertEquals("Victor", pessoaRepository.findById(1L).get().getNome());
    }
}
