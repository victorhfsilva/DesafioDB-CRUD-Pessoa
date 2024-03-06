package com.db.crudpessoabackend.domain.usuario.contato;

import com.db.crudpessoabackend.domain.base.BaseEntityAudit;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
@Table(name = "contatos")
public class Contato extends BaseEntityAudit {

    private String email;
    private String celular;

    @OneToOne(mappedBy = "contato")
    private Pessoa pessoa;

    public Contato(String createdBy, 
                    String updatedBy, 
                    String deactivatedBy, 
                    LocalDateTime createdAt, 
                    LocalDateTime updatedAt, 
                    LocalDateTime deactivatedAt, 
                    String email, 
                    String celular,
                    Pessoa pessoa) {
        super(createdBy, updatedBy, deactivatedBy, createdAt, updatedAt, deactivatedAt);
        this.email = email;
        this.celular = celular;
        this.pessoa = pessoa;
    }

}