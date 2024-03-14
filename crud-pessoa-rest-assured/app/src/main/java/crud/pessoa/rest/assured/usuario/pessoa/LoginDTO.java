package crud.pessoa.rest.assured.usuario.pessoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginDTO {
    String cpf;
    String senha;
}
