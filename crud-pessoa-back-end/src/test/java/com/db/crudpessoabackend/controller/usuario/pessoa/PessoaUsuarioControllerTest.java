package com.db.crudpessoabackend.controller.usuario.pessoa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoDTO;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PessoaUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PessoaUsuarioController pessoaUsuarioController;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PessoaService pessoaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configuracao(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void dadoUmUsuario_quandoRequisitadoOsDados_deveRetornarSeusDados() throws Exception{
        
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
        
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/dados")
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    void dadoUmUsuario_quandoDesativado_deveRetornarSeusDados() throws Exception{
        
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
        
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
        when(pessoaService.desativar(eq("73565638435"), any())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/desativar")
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    void dadoUmUsuario_quandoAtivado_deveRetornarSeusDados() throws Exception {
        
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
        
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
        when(pessoaService.ativar(eq("73565638435"), any())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/ativar")
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    void dadoUmUsuario_quandoAtualizadoComUsuarioValido_deveRetornarDadosAtualizados() throws Exception {
        
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
        
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuario/atualizar")
                                                .contentType("application/json")
                                                .content(pessoaJson)
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isOk())
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("73565638435"))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome"));
    }

    @Test
    void dadoUmUsuario_quandoAtualizadoComCpfInvalido_deveRetornarErro() throws Exception {
        
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
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);
        
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuario/atualizar")
                                                .contentType("application/json")
                                                .content(pessoaJson)
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void dadoUmUsuario_quandoAtualizadoComSenhaFraca_deveRetornarErro() throws Exception {
        
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
                                                    .senha("senha123")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
        
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);
        
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);

        when(tokenUtils.validarToken("Bearer tokenValido")).thenReturn("tokenValido");
        when(tokenService.obterSujeito("tokenValido")).thenReturn("73565638435");
        when(pessoaService.buscarPorCpf("73565638435")).thenReturn(pessoa);
        when(pessoaService.atualizar(eq("73565638435"), any(), any())).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuario/atualizar")
                                                .contentType("application/json")
                                                .content(pessoaJson)
                                                .header("Authorization", "Bearer tokenValido"))
                                                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
