package com.db.crudpessoabackend.domain.base;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
}
