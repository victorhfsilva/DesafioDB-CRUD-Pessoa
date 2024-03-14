package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.usuario.contato.dtos.ContatoRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

@Getter
@AllArgsConstructor
public class RespostaAtualizarPessoaDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private ContatoRespostaDTO contato;

    public RespostaAtualizarPessoaDTO(Pessoa pessoa){
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.sobrenome = pessoa.getSobrenome();
        this.cpf = pessoa.getCpf();
        this.dataDeNascimento = pessoa.getDataDeNascimento();
        this.contato = new ContatoRespostaDTO(pessoa.getContato());
    }
}
