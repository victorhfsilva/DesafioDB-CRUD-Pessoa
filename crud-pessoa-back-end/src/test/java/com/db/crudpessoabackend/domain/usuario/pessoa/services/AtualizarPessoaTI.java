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
import com.db.crudpessoabackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.AtualizarPessoaService;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;

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
    void dadaUmaPessoaValidaSalvaNoBancoDeDados_QuandoContatoAtualizado_DeveRetornarPessoaCorretaPorId(){
        Pessoa pessoa = pessoaRepository.findByCpf("198.654.156-11")
                                        .orElseThrow(() -> 
                                        new EntidadeNaoEncontradaException());

        Contato novoContato = contatoBuilder.celular("123457891")
                                        .email(pessoa.getContato().getEmail())
                                        .build();
        
        pessoa.setContato(novoContato);

        atualizarPessoaService.atualizar("198.654.156-11", pessoa);
        Pessoa pessoaAtualizada = pessoaRepository.findByCpf("198.654.156-11")
                                                    .orElseThrow(() -> 
                                                    new EntidadeNaoEncontradaException());

        assertEquals(novoContato.getCelular(), pessoaAtualizada.getContato().getCelular());

    }
}
