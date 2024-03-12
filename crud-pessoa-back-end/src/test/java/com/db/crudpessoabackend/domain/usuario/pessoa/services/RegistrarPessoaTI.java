package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.time.LocalDate;
import java.util.List;
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
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.RegistrarPessoaService;

@SpringBootTest
@ActiveProfiles("test")
class RegistrarPessoaTI {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private RegistrarPessoaService registrarPessoaService;

    private ContatoBuilder contatoBuilder;
    
    private EnderecoBuilder enderecoBuilder;

    private PessoaBuilder pessoaBuilder;
    
    @BeforeEach 
    private void configurar() {
        pessoaRepository.deleteAll();
        enderecoRepository.deleteAll();
        contatoRepository.deleteAll();

        contatoBuilder = new ContatoBuilder();
        enderecoBuilder = new EnderecoBuilder();
        pessoaBuilder = new PessoaBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmaPessoaValida_QuandoSalvaNoBancoDeDados_DeveRetornarPessoaPorId(){
        
        Contato contato = contatoBuilder.celular("(12) 34561-4567")
                                            .email("meu_email@email.com")
                                            .build();

        Endereco endereco1 = enderecoBuilder.numero("12-a")
                                        .rua("Rua A")
                                        .bairro("Bairro A")
                                        .cidade("Cidade A")
                                        .estado(Estado.ACRE)
                                        .cep("12345-758")
                                        .build();

        Endereco endereco2 = enderecoBuilder.reset()
                                .numero("14")
                                .rua("Rua B")
                                .bairro("Bairro B")
                                .cidade("Cidade B")
                                .estado(Estado.ALAGOAS)
                                .cep("97561-758")
                                .build();

        List<Endereco> enderecos = List.of(endereco1, endereco2);

        Pessoa pessoa = pessoaBuilder.active(true)
                                .nome("João")
                                .sobrenome("da Silva")
                                .cpf("223.356.7389-00")
                                .senha("senha123")
                                .papel(Papel.USUARIO)
                                .dataDeNascimento(LocalDate.of(1990, 5, 15))
                                .contato(contato)
                                .enderecos(enderecos)
                                .build();

        Pessoa pessoaSalva = registrarPessoaService.registrar(pessoa, pessoa);
        
        List<Endereco> actualEnderecos = pessoaRepository.findById(pessoaSalva.getId()).get().getEnderecos();
        String actualCpf = pessoaRepository.findById(pessoaSalva.getId()).get().getCpf();
        String actualEmail = pessoaRepository.findById(pessoaSalva.getId()).get().getContato().getEmail();

        assertIterableEquals(enderecos, actualEnderecos);
        assertEquals(pessoa.getCpf(), actualCpf);
        assertEquals(pessoa.getContato().getEmail(), actualEmail);
    }

    @Test
    void dadaUmaPessoaSemContatoOuEndereco_QuandoSalvaNoBancoDeDados_DeveRetornarPessoaPorId(){
        
        Pessoa pessoa = pessoaBuilder.nome("João")
                                .sobrenome("da Silva")
                                .cpf("223.356.7389-00")
                                .senha("senha123")
                                .papel(Papel.USUARIO)
                                .dataDeNascimento(LocalDate.of(1990, 5, 15))
                                .build();

        Pessoa pessoaSalva = registrarPessoaService.registrar(pessoa, null);
        
        String actualCpf = pessoaRepository.findById(pessoaSalva.getId()).get().getCpf();
        assertEquals(pessoa.getCpf(), actualCpf);
    }
}
