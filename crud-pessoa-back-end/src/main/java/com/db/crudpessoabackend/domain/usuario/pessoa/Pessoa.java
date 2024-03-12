package com.db.crudpessoabackend.domain.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.base.EntidadeBaseAudicao;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@ToString()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pessoas")
public class Pessoa extends EntidadeBaseAudicao {

    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;
    
    @Column(name = "sobrenome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String sobrenome;
    
    @Column(name = "cpf", unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    private String cpf;
    
    @Column(name = "senha", nullable = false, columnDefinition = "VARCHAR(1023)")
    @JsonIgnore
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private Papel papel;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Endereco> enderecos;

    public Pessoa(
            boolean active,
            String createdBy, 
            String updatedBy, 
            String deactivatedBy, 
            LocalDateTime createdAt, 
            LocalDateTime updatedAt, 
            LocalDateTime deactivatedAt,
            String nome, 
            String sobrenome, 
            String cpf, 
            String senha, 
            Papel papel, 
            LocalDate dataDeNascimento,
            Contato contato,
            List<Endereco> enderecos) {      
        super(active, createdBy, updatedBy, deactivatedBy, createdAt, updatedAt, deactivatedAt);
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.senha = senha;
        this.papel = papel;
        this.dataDeNascimento = dataDeNascimento;
        this.contato = contato;
        this.enderecos = enderecos;
    }
}
