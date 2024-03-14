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
import org.springframework.security.test.context.support.WithMockUser;
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
public class EnderecoAdminControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EnderecoAdminController enderecoAdminController;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private EnderecoService enderecoService;


    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
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
                                
        Contato contatoEditor = contatoBuilder.reset()
                                                .celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Pessoa editor  = pessoaBuilder.reset()
                                        .cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("admin");
        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);
    
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

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/endereco/adicionar/73565638435")
                                        .contentType("application/json")
                                        .content(enderecoJson)
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isCreated())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("126"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuarioSalvo_quandoDeletadoUmEndereco_deveRetornarEndereco() throws Exception{
       
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();

        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Pessoa pessoa  = pessoaBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .build();
        
        Endereco endereco = enderecoBuilder.numero("126")
                                            .complemento("Ap. 204")
                                            .rua("Rua")
                                            .bairro("Bairro")
                                            .cidade("Salvador")
                                            .estado(Estado.BAHIA)
                                            .cep("1234567")
                                            .pessoa(pessoa)
                                            .build();


        Contato contatoEditor = contatoBuilder.reset()
                                                .celular("123456789")
                                                .email("admin@db.com")
                                                .build();

        Pessoa editor  = pessoaBuilder.reset()
                                        .cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("admin");
        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);
        
        when(enderecoService.buscarEnderecoPorId(1L)).thenReturn(endereco);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);

        when(enderecoService.excluir(1L)).thenReturn(endereco);

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/endereco/excluir/1")
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("126"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmUsuarioSalvo_quandoAtualizaUmEndereco_deveRetornarEnderecoCorreto() throws Exception{
       
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        ContatoBuilder contatoBuilder = new ContatoBuilder();
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();

        Contato contato = contatoBuilder.celular("123456789")
                                        .email("pessoa@db.com")
                                        .build();

        Pessoa pessoa  = pessoaBuilder.nome("Nome")
                                        .sobrenome("Sobrenome")
                                        .cpf("73565638435")
                                        .senha("L33tP@swd")
                                        .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                        .contato(contato)
                                        .build();
        
        Endereco endereco = enderecoBuilder.numero("126")
                                            .complemento("Ap. 204")
                                            .rua("Rua")
                                            .bairro("Bairro")
                                            .cidade("Salvador")
                                            .estado(Estado.BAHIA)
                                            .cep("1234567")
                                            .pessoa(pessoa)
                                            .build();

        Contato contatoEditor = contatoBuilder.reset()
                                        .celular("123456789")
                                        .email("admin@db.com")
                                        .build();

        Pessoa editor  = pessoaBuilder.reset()
                                        .cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .contato(contatoEditor)
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("admin");
        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(enderecoService.buscarEnderecoPorId(1L)).thenReturn(endereco);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);

        EnderecoDTO enderecoDTO = EnderecoDTO.builder().numero("128")
                                                        .complemento("Casa")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        String enderecoJson = objectMapper.writeValueAsString(enderecoDTO);

        Endereco novoEndereco = enderecoDTO.converterParaEntidade();
        
        when(enderecoService.atualizar(eq(1L),any())).thenReturn(novoEndereco);

        mockMvc.perform(MockMvcRequestBuilders.put("/admin/endereco/atualizar/1")
                                        .contentType("application/json")
                                        .content(enderecoJson)
                                        .header("Authorization", "Bearer tokenValido"))
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.numero").value("128"))
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value("1234567"));
    }
}
