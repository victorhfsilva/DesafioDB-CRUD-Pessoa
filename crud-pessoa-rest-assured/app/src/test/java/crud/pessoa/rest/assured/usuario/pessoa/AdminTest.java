package crud.pessoa.rest.assured.usuario.pessoa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import crud.pessoa.rest.assured.usuario.contato.ContatoDTO;
import crud.pessoa.rest.assured.usuario.endereco.EnderecoDTO;
import crud.pessoa.rest.assured.usuario.estado.Estado;
import static org.hamcrest.Matchers.equalTo;
import java.time.LocalDate;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminTest {
    
    public static RequestSpecification requisicao;

    public static String token;

    public static ObjectMapper objectMapper;

    @BeforeAll
    public static void configuracao() {
        RestAssured.baseURI = "http://localhost:8080/";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void login() {
        requisicao = RestAssured.given()
                                .contentType("application/json");
        
        LoginDTO login = LoginDTO.builder().cpf("admin")
                                    .senha("admin")
                                    .build();
        
        Response resposta = requisicao.body(login)
                                        .when().post("/login");

        token = resposta.getBody().asString();
    }

    @Test
    @Order(1)
    public void testBuscarPessoas() {
        requisicao.header("Authorization", "Bearer " + token)
                    .when().get("/pessoas")
                    .then().assertThat().statusCode(200)
                    .and().body("_embedded.pessoas[0].nome", equalTo("Admin"))
                    .and().body("_embedded.pessoas[0].cpf", equalTo("admin"));
    }

    @Test
    @Order(2)
    public void testDesativarUsuario() throws JsonProcessingException {
        requisicao.header("Authorization", "Bearer " + token)
                    .when().patch("/admin/desativar/" + "admin")
                    .then().assertThat().statusCode(200);

                    
        requisicao.header("Authorization", "Bearer " + token)
                    .when().get("/pessoas")
                    .then().assertThat().statusCode(200)
                    .and().body("_embedded.pessoas[0].ativo", equalTo(false));
    }

    @Test
    @Order(3)
    public void testAtivarUsuario() throws JsonProcessingException {
        requisicao.header("Authorization", "Bearer " + token)
                    .when().patch("/admin/ativar/" + "admin")
                    .then().assertThat().statusCode(200);

        requisicao.header("Authorization", "Bearer " + token)
                    .when().get("/pessoas")
                    .then().assertThat().statusCode(200)
                    .and().body("_embedded.pessoas[0].ativo", equalTo(true));
    }

    @Test
    @Order(4)
    public void testRegistrarAdministrador() throws JsonProcessingException {
        ContatoDTO contatoDTO = ContatoDTO.builder().email("adalbertoPereira@email.com")
                                                    .celular("5521998456541")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("123")
                                                        .complemento("Casa")
                                                        .rua("Rua das Flores")
                                                        .bairro("Centro")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("01234567")
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

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Adalberto")
                                                    .sobrenome("Pereira")
                                                    .cpf("32878552806")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
       
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        requisicao.header("Authorization", "Bearer " + token)
                    .body(pessoaJson)
                    .when().post("/registrar/admin")
                    .then().assertThat().statusCode(201)
                    .and().body("pessoa.nome", equalTo("Adalberto"))
                    .and().body("pessoa.cpf", equalTo("32878552806"));
    }

    @Test
    @Order(5)
    public void testAtualizarAdministrador() throws JsonProcessingException {
        ContatoDTO contatoDTO = ContatoDTO.builder().email("adalbertoPereira@email.com")
                                                    .celular("5521998456541")
                                                    .build();

        AtualizarPessoaDTO pessoaDTO = AtualizarPessoaDTO.builder().nome("Adalberto")
                                                    .sobrenome("Mendonça Pereira")
                                                    .cpf("32878552806")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .build();
       
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        requisicao.header("Authorization", "Bearer " + token)
                    .body(pessoaJson)
                    .when().put("/admin/atualizar/32878552806?papel=ADMIN")
                    .then().assertThat().statusCode(200)
                    .and().body("sobrenome", equalTo("Mendonça Pereira"));
    }

    @Test
    @Order(6)
    public void testExcluirUsuario() throws JsonProcessingException {
        requisicao.header("Authorization", "Bearer " + token)
                    .when().delete("/admin/excluir/" + "32878552806")
                    .then().assertThat().statusCode(200);
                   
        requisicao.header("Authorization", "Bearer " + token)
                    .when().get("/pessoas")
                    .then().assertThat().statusCode(200)
                    .and().body("page.totalElements", equalTo(1));
    }
}
