package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
class RegistrarPessoaTest {

    @Autowired
    @InjectMocks
    private RegistrarPessoaService registrarPessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
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

    @Test
    void dadaUmaPessoaValida_QuandoSalva_DeveRetornarPessoaPorId(){
        
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

        Pessoa pessoa = pessoaBuilder.nome("João")
                                .sobrenome("da Silva")
                                .cpf("223.356.7389-00")
                                .senha("senha123")
                                .papel(Papel.USUARIO)
                                .dataDeNascimento(LocalDate.of(1990, 5, 15))
                                .contato(contato)
                                .enderecos(enderecos)
                                .build();

        when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoa));
        when(contatoRepository.save(contato)).thenReturn(contato);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(enderecoRepository.save(endereco1)).thenReturn(endereco1);
        when(enderecoRepository.save(endereco2)).thenReturn(endereco2);

        Pessoa pessoaSalva = registrarPessoaService.registrar(pessoa);
        
        verify(contatoRepository).save(contato);
        verify(pessoaRepository).save(pessoa);
        verify(enderecoRepository).save(endereco1);
        verify(enderecoRepository).save(endereco2);
        
        assertIterableEquals(pessoa.getEnderecos(), pessoaSalva.getEnderecos());
        assertEquals(pessoa.getCpf(), pessoaSalva.getCpf());
        assertEquals(pessoa.getContato().getEmail(), pessoaSalva.getContato().getEmail());
    }
}
