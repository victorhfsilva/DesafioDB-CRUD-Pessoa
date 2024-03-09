package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import java.time.LocalDate;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;

@SpringBootTest
@ActiveProfiles("test")
class AtualizarEnderecoServiceTI {
    
    @Autowired
    private AtualizarEnderecoService atualizarEnderecoService;

    @Autowired
    private PessoaRepository pessoaRepository;

    private EnderecoBuilder enderecoBuilder;

    private PessoaBuilder pessoaBuilder;

    @BeforeEach 
    private void configurar() {
        enderecoBuilder = new EnderecoBuilder();
        pessoaBuilder = new PessoaBuilder();
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/reiniciar-ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmEnderecoValidoSalvo_QuandoAtualizado_DeveRetornarEnderecoCorreto() {
        
        Pessoa pessoa = pessoaRepository.findByRua("Rua A").get(0);
        
        Endereco novoEndereco = enderecoBuilder.numero("12-a")
                                .rua("Rua A")
                                .bairro("Bairro A")
                                .cidade("Cidade A")
                                .estado(Estado.ACRE)
                                .cep("12345-758")
                                .pessoa(pessoa)
                                .build();
    
        Endereco enderecoSalvo = atualizarEnderecoService.atualizar(1L, novoEndereco);

        assertEquals(novoEndereco, enderecoSalvo);
    }


    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/reiniciar-ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmEnderecoValidoSalvo_QuandoAtualizadoAPessoa_DeveRetornarPessoaCorreta() {
        
        Pessoa pessoa = pessoaRepository.findByRua("Rua B").get(0);
        
        Endereco novoEndereco = enderecoBuilder.numero("12-a")
                                .rua("Rua A")
                                .bairro("Bairro A")
                                .cidade("Cidade A")
                                .estado(Estado.ACRE)
                                .cep("12345-758")
                                .pessoa(pessoa)
                                .build();
    
        Endereco enderecoSalvo = atualizarEnderecoService.atualizar(1L, novoEndereco);

        assertEquals(pessoa.getCpf(), enderecoSalvo.getPessoa().getCpf());
    }

    @Test
    @SqlGroup({
        @Sql(scripts =  "/db/limpar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/dados.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/reiniciar-ids.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void dadaUmEnderecoValidoSalvo_QuandoPessoaNaoEstiverCadastrada_DeveLancarExcecao() {
        
        Pessoa pessoa = pessoaBuilder.nome("João")
                                        .sobrenome("da Silva")
                                        .cpf("223.356.7389-00")
                                        .senha("senha123")
                                        .papel(Papel.USUARIO)
                                        .dataDeNascimento(LocalDate.of(1990, 5, 15))
                                        .build();
        
        Endereco novoEndereco = enderecoBuilder.numero("12-a")
                                .rua("Rua A")
                                .bairro("Bairro A")
                                .cidade("Cidade A")
                                .estado(Estado.ACRE)
                                .cep("12345-758")
                                .pessoa(pessoa)
                                .build();
    
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            atualizarEnderecoService.atualizar(1L, novoEndereco);
        });
    }
}
