package com.db.crudpessoabackend.domain.usuario.contato;

import com.db.crudpessoabackend.domain.BaseEntityAudit;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode(callSuper=false, exclude = {"pessoa"})
@ToString(exclude = {"pessoa"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "contatos")
public class Contato extends BaseEntityAudit {

    private String email;
    private String celular;

    @OneToOne(mappedBy = "contato")
    private Pessoa pessoa;
}