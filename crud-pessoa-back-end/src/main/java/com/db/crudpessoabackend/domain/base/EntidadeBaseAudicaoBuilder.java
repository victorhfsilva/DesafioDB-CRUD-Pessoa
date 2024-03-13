package com.db.crudpessoabackend.domain.base;

import com.db.crudpessoabackend.domain.base.interfaces.IEntidadeBaseAudicaoBuilder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public abstract class EntidadeBaseAudicaoBuilder<T extends EntidadeBaseAudicaoBuilder<T>> implements IEntidadeBaseAudicaoBuilder{

    private boolean ativo;
    private String criadoPor;
    private String atualizadoPor;
    private String desativadoPor;
    private LocalDateTime criadoAs;
    private LocalDateTime atualizadoAs;
    private LocalDateTime desativadoAs;
    
    @Override
    public T ativo(boolean active) {
        this.ativo = active;
        return self();
    }

    @Override
    public T criadoPor(String email) {
        this.criadoPor = email;
        return self();
    }

    @Override
    public T atualizadoPor(String email) {
        this.atualizadoPor = email;
        return self();
    }

    @Override
    public T desativadoPor(String email) {
        this.desativadoPor = email;
        return self();
    }

    @Override
    public T criadoAs(LocalDateTime timestamp) {
        this.criadoAs = timestamp;
        return self();
    }

    @Override
    public T atualizadoAs(LocalDateTime timestamp) {
        this.atualizadoAs = timestamp;
        return self();
    }

    @Override
    public T desativadoAs(LocalDateTime timestamp) {
        this.desativadoAs = timestamp;
        return self();
    }
    
    protected abstract T self();
}
