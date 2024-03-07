package com.db.crudpessoabackend.domain.usuario.pessoa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;

@SpringBootTest
@ActiveProfiles("test")
class PessoaRepositoryTest {
 
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private ContatoBuilder contatoBuilder;
    
    private EnderecoBuilder enderecoBuilder;

    private PessoaBuilder pessoaBuilder;
    
    @BeforeEach 
    private void configurar() {
        contatoBuilder = new ContatoBuilder();
        enderecoBuilder = new EnderecoBuilder();
        pessoaBuilder = new PessoaBuilder();
    }

    @AfterEach
    private void finalizar() {
        pessoaRepository.deleteAll();
        enderecoRepository.deleteAll();
        contatoRepository.deleteAll();
    }

    @Test
    void dadaUmaPessoaValida_QuandoSalva_DeveRetornarPessoaPorId(){
        
        Contato contato = contatoBuilder.celular("(12) 34561-4567")
                                            .email("meu_email@email.com")
                                            .build();
        Contato contatoSalvo = contatoRepository.save(contato);
        
        Pessoa pessoa = pessoaBuilder.nome("João")
                                        .sobrenome("da Silva")
                                        .cpf("223.356.7389-00")
                                        .senha("senha123")
                                        .papel(Papel.USUARIO)
                                        .dataDeNascimento(LocalDate.of(1990, 5, 15))
                                        .contato(contatoSalvo)
                                        .enderecos(List.of())
                                        .build();

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Endereco endereco1 = enderecoBuilder.numero("12-a")
                                            .rua("Rua A")
                                            .bairro("Bairro A")
                                            .cidade("Cidade A")
                                            .estado(Estado.ACRE)
                                            .cep("12345-758")
                                            .pessoa(pessoaSalva)
                                            .build();
        Endereco enderecoSalvo1 = enderecoRepository.save(endereco1);

        Endereco endereco2 = enderecoBuilder.reset()
                                    .numero("14")
                                    .rua("Rua B")
                                    .bairro("Bairro B")
                                    .cidade("Cidade B")
                                    .estado(Estado.ALAGOAS)
                                    .cep("97561-758")
                                    .pessoa(pessoaSalva)
                                    .build();
        Endereco enderecoSalvo2 = enderecoRepository.save(endereco2);

        List<Endereco> expectedEnderecos = List.of(enderecoSalvo1, enderecoSalvo2);
        
        List<Endereco> actualEnderecos = pessoaRepository.findById(pessoaSalva.getId()).get().getEnderecos();
        String actualCpf = pessoaRepository.findById(pessoaSalva.getId()).get().getCpf();

        assertIterableEquals(expectedEnderecos, actualEnderecos);
        assertEquals(pessoa.getCpf(), actualCpf);
    }
}
