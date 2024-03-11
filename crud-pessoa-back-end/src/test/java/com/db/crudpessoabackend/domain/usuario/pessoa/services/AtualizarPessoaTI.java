package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.AtualizarPessoaService;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;

@SpringBootTest
@ActiveProfiles("test")
class AtualizarPessoaTI {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AtualizarPessoaService atualizarPessoaService;

    private ContatoBuilder contatoBuilder;
    
    
    @BeforeEach 
    private void configurar() {
        contatoBuilder = new ContatoBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoContatoEhAtualizado_DeveRetornarPessoaCorretaPorId(){
        Pessoa pessoa = pessoaRepository.findByCpf("198.654.156-11")
                                        .orElseThrow(EntidadeNaoEncontradaException::new);

        Contato novoContato = contatoBuilder.celular("123457891")
                                        .email(pessoa.getContato().getEmail())
                                        .build();
        
        pessoa.setContato(novoContato);

        atualizarPessoaService.atualizar("198.654.156-11", pessoa);
        Pessoa pessoaAtualizada = pessoaRepository.findByCpf("198.654.156-11")
                                                    .orElseThrow(EntidadeNaoEncontradaException::new);

        assertEquals(novoContato.getCelular(), pessoaAtualizada.getContato().getCelular());
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoSobrenomeEhAtualizado_DeveRetornarPessoaCorretaPorId(){
        Pessoa pessoa = pessoaRepository.findByCpf("198.654.156-11")
                                        .orElseThrow(EntidadeNaoEncontradaException::new);
       
        pessoa.setSobrenome("Miralles");

        atualizarPessoaService.atualizar("198.654.156-11", pessoa);
        Pessoa pessoaAtualizada = pessoaRepository.findByCpf("198.654.156-11")
                                                    .orElseThrow(EntidadeNaoEncontradaException::new);

        assertEquals("Miralles", pessoaAtualizada.getSobrenome());
    }

}
