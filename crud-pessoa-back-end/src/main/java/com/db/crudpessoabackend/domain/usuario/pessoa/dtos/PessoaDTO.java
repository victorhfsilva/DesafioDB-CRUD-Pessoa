package com.db.crudpessoabackend.domain.usuario.pessoa.dtos;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.dtos.ContatoDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoDTO;
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
public class PessoaDTO {
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

    @NotNull
    private List<EnderecoDTO> enderecos;

    public Pessoa converterParaEntidade(PasswordEncoder passwordEncoder, Papel papel){
        PessoaBuilder pessoaBuilder = new PessoaBuilder();
        
        Contato contatoEntidade = contato.converterParaEntidade();

        List<Endereco> enderecosEntidade = enderecos.stream().map(endereco -> 
                                                            endereco.converterParaEntidade())
                                                            .toList();
        
        return pessoaBuilder.nome(nome)
                            .sobrenome(sobrenome)
                            .cpf(cpf)
                            .senha(passwordEncoder.encode(senha))
                            .papel(papel)
                            .dataDeNascimento(dataDeNascimento)
                            .contato(contatoEntidade)
                            .enderecos(enderecosEntidade)
                            .build();
    }

    public Pessoa converterParaEntidadeSemEndereco(PasswordEncoder passwordEncoder, Papel papel){
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
