package com.db.crudpessoabackend.domain.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.base.EntidadeBaseAudicaoBuilder;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaBuilder;

public class PessoaBuilder extends EntidadeBaseAudicaoBuilder<PessoaBuilder> implements IPessoaBuilder{

    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;
    private Papel papel;
    private LocalDate dataDeNascimento;
    private Contato contato;
    private List<Endereco> enderecos;

    @Override
    public PessoaBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public PessoaBuilder sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    @Override
    public PessoaBuilder cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public PessoaBuilder senha(String senha) {
        this.senha = senha;
        return this;
    }

    @Override
    public PessoaBuilder papel(Papel papel) {
        this.papel = papel;
        return this;
    }

    @Override
    public PessoaBuilder dataDeNascimento(LocalDate dataNascimento) {
        this.dataDeNascimento = dataNascimento;
        return this;
    }

    @Override
    public PessoaBuilder contato(Contato contato) {
        this.contato = contato;
        return this;
    }

    @Override
    public PessoaBuilder enderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    @Override
    public Pessoa build() {
        return new Pessoa(
            isActive(),
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
    public PessoaBuilder reset() {
        return new PessoaBuilder();
    }

    @Override
    protected PessoaBuilder self() {
        return this;
    }

}
