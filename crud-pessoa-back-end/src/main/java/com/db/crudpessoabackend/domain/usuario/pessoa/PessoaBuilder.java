package com.db.crudpessoabackend.domain.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.base.BaseEntityAuditBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaBuilder;

public class PessoaBuilder extends BaseEntityAuditBuilder implements IPessoaBuilder{

    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;
    private Papel papel;
    private LocalDate dataDeNascimento;
    private Contato contato;
    private List<Endereco> enderecos;

    @Override
    public IPessoaBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public IPessoaBuilder sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    @Override
    public IPessoaBuilder cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public IPessoaBuilder senha(String senha) {
        this.senha = senha;
        return this;
    }

    @Override
    public IPessoaBuilder papel(Papel papel) {
        this.papel = papel;
        return this;
    }

    @Override
    public IPessoaBuilder dataDeNascimento(LocalDate dataNascimento) {
        this.dataDeNascimento = dataNascimento;
        return this;
    }

    @Override
    public IPessoaBuilder contato(Contato contato) {
        this.contato = contato;
        return this;
    }

    @Override
    public IPessoaBuilder enderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    @Override
    public Pessoa build() {
        return new Pessoa(
            getCreatedBy(),
            getUpdatedBy(),
            getDeactivatedBy(),
            getCreatedAt(),
            getUpdatedAt(),
            getDeactivatedAt(),
            nome,
            sobrenome,
            cpf,
            senha,
            papel,
            dataDeNascimento,
            contato,
            enderecos
        );
    }

    @Override
    public IPessoaBuilder reset() {
        return new PessoaBuilder();
    }

}
