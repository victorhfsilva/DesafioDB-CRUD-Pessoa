package com.db.crudpessoabackend.domain.usuario.pessoa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.BuscarPessoaPorCpf;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.MostrarIdadeService;

@ExtendWith(MockitoExtension.class)
class MostrarIdadeServicoTest {
    
    @InjectMocks 
    MostrarIdadeService mostrarIdadeService;

    @Mock
    BuscarPessoaPorCpf buscarPessoaPorCpf;

    PessoaBuilder pessoaBuilder;

    @BeforeEach
    void configurar() {
        pessoaBuilder = new PessoaBuilder();
    }

    @ParameterizedTest
    @MethodSource("idades")
    void dadaUmaPessoaValida_QuandoSolicitadaIdade_DeveRetornarIdadeCorreta(LocalDate dataDeNascimento, int idadeEsperada) {
        String cpf = "223.356.7389-00";
        Pessoa pessoa = pessoaBuilder.cpf(cpf).dataDeNascimento(dataDeNascimento).build();
        when(buscarPessoaPorCpf.buscarPorCpf(cpf)).thenReturn(pessoa);

        int idadeRetornada = mostrarIdadeService.mostrarIdade(cpf);

        assertEquals(idadeEsperada, idadeRetornada);
    }

    /**
     * TODO: Estes argumentos só são válidos até dia 31/12/2024.
     */
    private static Stream<Arguments> idades() {
        return Stream.of(
            Arguments.of(LocalDate.of(1994, 01, 01), 30),
            Arguments.of(LocalDate.of(2005, 01, 01), 19),
            Arguments.of(LocalDate.of(1983, 01, 01), 41)
        );
    }


}
