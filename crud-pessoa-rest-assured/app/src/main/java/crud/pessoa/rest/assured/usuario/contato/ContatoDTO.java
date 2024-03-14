package crud.pessoa.rest.assured.usuario.contato;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ContatoDTO {
    private String email;
    private String celular;
}
