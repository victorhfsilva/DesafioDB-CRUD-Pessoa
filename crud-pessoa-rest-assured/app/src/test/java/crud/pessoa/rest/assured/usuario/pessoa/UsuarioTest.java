package crud.pessoa.rest.assured.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.hamcrest.Matchers.equalTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import crud.pessoa.rest.assured.usuario.contato.ContatoDTO;
import crud.pessoa.rest.assured.usuario.endereco.EnderecoDTO;
import crud.pessoa.rest.assured.usuario.estado.Estado;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTest {

    public static RequestSpecification requisicao;

    public static String token;

    public static ObjectMapper objectMapper;

    @BeforeAll
    public static void registrarUsuario() throws JsonProcessingException {
        RestAssured.baseURI = "http://localhost:8080";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ContatoDTO contatoDTO = ContatoDTO.builder().email("adalbertaSilveira@email.com")
                                                    .celular("5511997453521")
                                                    .build();

        EnderecoDTO enderecoDTO1 = EnderecoDTO.builder().numero("126")
                                                        .complemento("Casa")
                                                        .rua("Rua das Rosas")
                                                        .bairro("Jardim Esperança")
                                                        .cidade("Salvador")
                                                        .estado(Estado.BAHIA)
                                                        .cep("01257527")
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

        PessoaDTO pessoaDTO = PessoaDTO.builder().nome("Adalberta")
                                                    .sobrenome("Silveira")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .enderecos(enderecosDTOs)
                                                    .build();
       
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        RestAssured.given()
                    .contentType("application/json")
                    .body(pessoaJson)
                    .when().post("/registrar/usuario")
                    .thenReturn();
    }

    @BeforeEach
    public void login() {
        requisicao = RestAssured.given()
                                .contentType("application/json");

        LoginDTO usuario = LoginDTO.builder().cpf("73565638435")
                                            .senha("L33tP@swd")
                                            .build();

        Response respostaLoginUsuario = RestAssured.given()
                                                    .contentType("application/json")
                                                    .body(usuario)
                                                    .when().post("/login");

        token = respostaLoginUsuario.getBody().asString(); 

    }
    
    @Test
    @Order(1)
    public void testBuscarDadosUsuario() {      
        requisicao.header("Authorization", "Bearer " + token)
                    .when().get("/usuario/dados")
                    .then().assertThat().statusCode(200)
                    .and().body("nome", equalTo("Adalberta"))
                    .and().body("cpf", equalTo("73565638435"));
    }

    @Test
    @Order(2)
    public void testDesativarUsuario() throws JsonProcessingException {        
        requisicao.header("Authorization", "Bearer " + token)
                    .when().patch("/usuario/desativar")
                    .then().assertThat().statusCode(200);
    }

    @Test
    @Order(3)
    public void testAtivarUsuario() throws JsonProcessingException {        
        requisicao.header("Authorization", "Bearer " + token)
                    .when().patch("/usuario/ativar")
                    .then().assertThat().statusCode(200);

    }



    @Test
    @Order(4)
    public void testAtualizarUsuario() throws JsonProcessingException {
       
        ContatoDTO contatoDTO = ContatoDTO.builder().email("adalbertaSilveiraMendonca@email.com")
                                                    .celular("5511997453521")
                                                    .build();

        AtualizarPessoaDTO pessoaDTO = AtualizarPessoaDTO.builder().nome("Adalberta")
                                                    .sobrenome("Silveira Mendonça")
                                                    .cpf("73565638435")
                                                    .senha("L33tP@swd")
                                                    .dataDeNascimento(LocalDate.of(2004, 3, 7))
                                                    .contato(contatoDTO)
                                                    .build();
       
        String pessoaJson = objectMapper.writeValueAsString(pessoaDTO);

        requisicao.header("Authorization", "Bearer " + token)
                    .body(pessoaJson)
                    .when().put("/usuario/atualizar")
                    .then().assertThat().statusCode(200)
                    .and().body("sobrenome", equalTo("Silveira Mendonça"));
    }


}

