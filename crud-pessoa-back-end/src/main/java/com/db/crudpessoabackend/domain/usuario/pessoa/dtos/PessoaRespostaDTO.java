package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.usuario.contato.dtos.ContatoRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PessoaRespostaDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private ContatoRespostaDTO contato;
    private List<EnderecoRespostaDTO> enderecos;

    public PessoaRespostaDTO(Pessoa pessoa){
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.sobrenome = pessoa.getSobrenome();
        this.cpf = pessoa.getCpf();
        this.dataDeNascimento = pessoa.getDataDeNascimento();
        this.contato = new ContatoRespostaDTO(pessoa.getContato());
        this.enderecos = pessoa.getEnderecos().stream()
                                                .map(EnderecoRespostaDTO::new)
                                                .toList();  
    }
}
