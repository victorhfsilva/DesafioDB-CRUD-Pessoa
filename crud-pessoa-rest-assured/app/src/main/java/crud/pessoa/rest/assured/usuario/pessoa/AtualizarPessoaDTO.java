package crud.pessoa.rest.assured.usuario.pessoa;

import java.time.LocalDate;
import crud.pessoa.rest.assured.usuario.contato.ContatoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AtualizarPessoaDTO {
    private String nome; 
    private String sobrenome;
    private String cpf;
    private String senha;
    private LocalDate dataDeNascimento;
    private ContatoDTO contato;
}
