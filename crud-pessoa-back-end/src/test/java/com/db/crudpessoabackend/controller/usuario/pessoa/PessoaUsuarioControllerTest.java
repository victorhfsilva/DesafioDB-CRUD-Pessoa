package com.db.crudpessoabackend.controller.usuario.pessoa;

import static org.mockito.Answers.values;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import com.db.crudpessoabackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PessoaUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PessoaUsuarioController pessoaUsuarioController;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PessoaService pessoaService;

    @Test
    void getDadosTest() throws Exception{
        
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        
        Pessoa pessoa = pessoaBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("123456789")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("123456789");
        when(pessoaService.buscarPorCpf("123456789")).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/dados")
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("123456789"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    void desativarTest() throws Exception{
        
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        
        Pessoa pessoa = pessoaBuilder.ativo(true)
                                        .nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("123456789")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("123456789");
        when(pessoaService.buscarPorCpf("123456789")).thenReturn(pessoa);
        when(pessoaService.desativar(eq("123456789"), any())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/desativar")
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("123456789"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }
}
