package com.db.crudpessoabackend.domain.usuario.pessoa;

import java.time.LocalDate;
import java.util.List;
import com.db.crudpessoabackend.domain.base.BaseEntityAudit;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
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
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper=false)
@ToString()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pessoas")
public class Pessoa extends BaseEntityAudit {

    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;
    
    @Column(name = "sobrenome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String sobrenome;
    
    @Column(name = "cpf", unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    private String cpf;
    
    @Column(name = "senha", nullable = false, columnDefinition = "VARCHAR(50)")
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private Papel papel;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @OneToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Endereco> enderecos;

    public Pessoa(String createdBy, 
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
        super(createdBy, updatedBy, deactivatedBy, createdAt, updatedAt, deactivatedAt);
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
