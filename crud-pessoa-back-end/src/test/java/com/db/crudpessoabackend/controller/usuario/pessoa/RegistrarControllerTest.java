package com.db.crudpessoabackend.controller.usuario.pessoa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import com.db.crudpessoabackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoDTO;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrarControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RegistrarController registrarController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenUtils tokenUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void dadoUmUsuarioValido_quandoRegistrado_DeveRetornarOk() throws Exception{
    
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        when(tokenService.gerarToken("73565638435")).thenReturn("tokenValido");
        when(pessoaService.registrar(any(), any())).thenReturn(pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/usuario")
                                                .contentType("application/json")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("tokenValido"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.pessoa.cpf").value(pessoaDTO.getCpf()));
    }

    @Test
    void dadoUmUsuarioComCpfInvalido_quandoRegistrado_DeveRetornarErro() throws Exception{
    
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638430")
                                                    .senha("L33tP@wd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        when(tokenService.gerarToken("73565638430")).thenReturn("tokenValido");
        when(pessoaService.registrar(any(), any())).thenReturn(pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/usuario")
                                                .contentType("application/json")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void dadoUmUsuarioComSenhaFraca_quandoRegistrado_DeveRetornarErro() throws Exception{
    
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("senha123")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        when(tokenService.gerarToken("73565638435")).thenReturn("tokenValido");
        when(pessoaService.registrar(any(), any())).thenReturn(pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO));
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/usuario")
                                                .contentType("application/json")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmAdminValido_quandoRegistrado_DeveRetornarOk() throws Exception{
    
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@wd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        Pessoa editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("admin");
        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(tokenService.gerarToken("73565638435")).thenReturn("outroTokenValido");
        when(pessoaService.registrar(any(), any())).thenReturn(pessoa);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/admin")
                                                .contentType("application/json")
                                                .header("Authorization", "Bearer tokenValido")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("outroTokenValido"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.pessoa.cpf").value(pessoaDTO.getCpf()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmAdminComCpfInvalido_quandoRegistrado_DeveRetornarErro() throws Exception{
    
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638430")
                                                    .senha("L33tP@wd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        Pessoa editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("admin");
        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(tokenService.gerarToken("73565638430")).thenReturn("outroTokenValido");
        when(pessoaService.registrar(any(), any())).thenReturn(pessoa);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/admin")
                                                .contentType("application/json")
                                                .header("Authorization", "Bearer tokenValido")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void dadoUmAdminComSenhaFraca_quandoRegistrado_DeveRetornarErro() throws Exception{
    
        ContatoDTO contatoDTO = ContatoDTO.builder().email("nome@email.com")
                                                    .celular("123456789")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 201")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        EnderecoDTO enderecoDTO2 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Ap. 204")
                                                        .rua("Rua")
                                                        .bairro("Bairro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("1234567")
                                                        .build();

        List<EnderecoDTO> enderecosDTOs = List.of(enderecoDTO1, enderecoDTO2);

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Nome")
                                                    .sobrenome("Sobrenome")
                                                    .cpf("73565638435")
                                                    .senha("senha123")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        Pessoa editor  = pessoaBuilder.cpf("admin")
                                        .nome("admin")
                                        .senha("admin")
                                        .build();

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("admin");
        when(pessoaService.buscarPorCpf("admin")).thenReturn(editor);

        when(tokenService.gerarToken("73565638435")).thenReturn("outroTokenValido");
        when(pessoaService.registrar(any(), any())).thenReturn(pessoa);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar/admin")
                                                .contentType("application/json")
                                                .header("Authorization", "Bearer tokenValido")
                                                .content(pessoaJson))
                                                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
