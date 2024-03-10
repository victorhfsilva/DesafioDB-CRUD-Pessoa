package com.db.crudpessoabackend.domain.usuario.endereco.dto;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import lombok.Getter;

@Getter
public class EnderecoRespostaDTO {
    private Long id;
    private String numero;
    private String complemento;
    private String rua;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;

    public EnderecoRespostaDTO(Endereco endereco){
        this.id = endereco.getId();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.rua = endereco.getRua();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
    }
}
