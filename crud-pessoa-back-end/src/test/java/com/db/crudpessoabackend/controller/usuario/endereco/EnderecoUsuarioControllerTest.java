package com.db.crudpessoabackend.controller.usuario.endereco;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.servicos.EnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.utils.EnderecoUtils;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EnderecoUsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EnderecoUsuarioController enderecoUsuarioController;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private EnderecoService enderecoService;

    @MockBean
    private EnderecoUtils enderecoUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void dadoUmUsuarioSalvo_quandoAdicionadoUmEndereco_deveRetornarEndereco() throws Exception{
       
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
       
        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Pessoa pessoa  = pessoaBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .enderecos(List.of())
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
    
        EnderecoDTO enderecoDTO = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        String enderecoJson = objectMapper.writeValueAsString(enderecoDTO);

        Endereco endereco = enderecoDTO.converterParaEntidade();
        
        when(enderecoService.adicionar(any())).thenReturn(endereco);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/endereco/adicionar")
                                        .contentType("application/json")
                                        .content(enderecoJson)
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isCreated())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("126"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }

    @Test
    void dadoUmUsuarioSalvo_quandoDeletadoUmEndereco_deveRetornarEndereco() throws Exception{
       
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();

        Endereco endereco = enderecoBuilder.numero("126")
                                            .complemento("Ap. 204")
                                            .rua("Rua")
                                            .bairro("Bairro")
                                            .cidade("Salvador")
                                            .estado(Estado.BAHIA)
                                            .cep("1234567")
                                            .build();

        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Pessoa pessoa  = pessoaBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .enderecos(List.of(endereco))
                                        .build();

        when(enderecoUtils.validarPermissaoDeAlterarEndereco("Bearer tokenValido", 1L)).thenReturn(pessoa);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);

        when(enderecoService.excluir(1L)).thenReturn(endereco);

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuario/endereco/excluir/1")
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("126"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }
}
