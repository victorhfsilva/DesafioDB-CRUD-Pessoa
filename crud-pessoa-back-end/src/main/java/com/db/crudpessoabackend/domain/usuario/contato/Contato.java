package com.db.crudpessoabackend.domain.usuario.contato;

import com.db.crudpessoabackend.domain.base.EntidadeBase;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false, exclude = {"pessoa"})
@ToString(exclude = {"pessoa"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "contatos")
public class Contato extends EntidadeBase {
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "celular", unique = true)
    private String celular;

    @OneToOne(mappedBy = "contato")
    private Pessoa pessoa;

}