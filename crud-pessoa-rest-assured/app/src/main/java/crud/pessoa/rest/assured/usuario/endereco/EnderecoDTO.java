package crud.pessoa.rest.assured.usuario.endereco;

import crud.pessoa.rest.assured.usuario.estado.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EnderecoDTO {
    private String numero;
    private String complemento;
    private String rua;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;
}
