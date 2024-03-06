package com.db.crudpessoabackend.domain.usuario.endereco;

import com.db.crudpessoabackend.domain.base.BaseEntityAudit;
import com.db.crudpessoabackend.domain.usuario.estado.Estado;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper=false, exclude = {"pessoa"})
@ToString(exclude = {"pessoa"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "enderecos")
public class Endereco extends BaseEntityAudit {
    
    @Column(name = "numero", nullable = false, columnDefinition = "VARCHAR(10)")
    private String numero;
    
    @Column(name = "complemento", nullable = true, columnDefinition = "VARCHAR(255)")
    private String complemento;

    @Column(name = "rua", nullable = false, columnDefinition = "VARCHAR(100)")
    private String rua;

    @Column(name = "bairro", nullable = false, columnDefinition = "VARCHAR(100)")
    private String bairro;

    @Column(name = "cidade", nullable = false, columnDefinition = "VARCHAR(50)")
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "cep", nullable = false, columnDefinition = "VARCHAR(50)")
    private String cep;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Endereco(String createdBy, 
                    String updatedBy, 
                    String deactivatedBy, 
                    LocalDateTime createdAt, 
                    LocalDateTime updatedAt, 
                    LocalDateTime deactivatedAt, 
                    String numero, 
                    String complemento, 
                    String rua, 
                    String bairro, 
                    String cidade, 
                    Estado estado, 
                    String cep,
                    Pessoa pessoa) {
        super(createdBy, updatedBy, deactivatedBy, createdAt, updatedAt, deactivatedAt);
        this.numero = numero;
        this.complemento = complemento;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pessoa = pessoa;
    }
}
