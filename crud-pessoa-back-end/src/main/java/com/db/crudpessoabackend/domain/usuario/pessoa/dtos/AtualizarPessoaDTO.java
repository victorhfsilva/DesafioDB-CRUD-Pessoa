package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.PessoaBuilder;
import com.db.crudpessoabackend.infra.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AtualizarPessoaDTO {
    @NotBlank
    private String nome;
    
    @NotBlank
    private String sobrenome;
    
    @CPF
    private String cpf;
    
    @ValidPassword
    private String senha;

    @NotNull
    private LocalDate dataDeNascimento;

    @NotNull
    private ContatoDTO contato;


    public Pessoa converterParaEntidade(PasswordEncoder passwordEncoder, Papel papel){
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        
        Contato contatoEntidade = contato.converterParaEntidade();
       
        return pessoaBuilder.nome(nome)
                            .sobrenome(sobrenome)
                            .cpf(cpf)
                            .senha(passwordEncoder.encode(senha))
                            .papel(papel)
                            .dataDeNascimento(dataDeNascimento)
                            .contato(contatoEntidade)
                            .build();
    }
}
