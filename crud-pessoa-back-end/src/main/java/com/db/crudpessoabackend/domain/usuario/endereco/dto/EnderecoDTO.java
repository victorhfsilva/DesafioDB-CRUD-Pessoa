package com.db.crudpessoabackend.domain.usuario.endereco.dto;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoBuilder;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EnderecoDTO {
    @NotBlank
    private String numero;

    private String complemento;

    @NotBlank
    private String rua;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotNull
    private Estado estado;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "A string deve conter apenas dígitos.")
    private String cep;

    public Endereco converterParaEntidade(){
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();
        return enderecoBuilder.bairro(bairro)
                                .cep(cep)
                                .cidade(cidade)
                                .complemento(complemento)
                                .estado(estado)
                                .numero(numero)
                                .rua(rua)
                                .build();
    }

    public Endereco converterParaEntidadeComDono(Pessoa pessoa){
        EnderecoBuilder enderecoBuilder = new EnderecoBuilder();
        return enderecoBuilder.bairro(bairro)
                                .cep(cep)
                                .cidade(cidade)
                                .complemento(complemento)
                                .estado(estado)
                                .numero(numero)
                                .rua(rua)
                                .pessoa(pessoa)
                                .build();
    }
}
