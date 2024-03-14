package crud.pessoa.rest.assured.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import crud.pessoa.rest.assured.usuario.contato.ContatoDTO;
import crud.pessoa.rest.assured.usuario.endereco.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PessoaDTO {
    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;
    private LocalDate dataDeNascimento;
    private ContatoDTO contato;
    private List<EnderecoDTO> enderecos;
}
