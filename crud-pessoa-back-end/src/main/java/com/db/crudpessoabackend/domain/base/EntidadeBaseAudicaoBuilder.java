package com.db.crudpessoabackend.domain.base;

import com.db.crudpessoabackend.domain.base.interfaces.IEntidadeBaseAudicaoBuilder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public abstract class EntidadeBaseAudicaoBuilder<T extends EntidadeBaseAudicaoBuilder<T>> implements IEntidadeBaseAudicaoBuilder{

    private boolean active;
    private String createdBy;
    private String updatedBy;
    private String deactivatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deactivatedAt;
    
    @Override
    public T active(boolean active) {
        this.active = active;
        return self();
    }

    @Override
    public T createdBy(String email) {
        this.createdBy = email;
        return self();
    }

    @Override
    public T updatedBy(String email) {
        this.updatedBy = email;
        return self();
    }

    @Override
    public T deactivatedBy(String email) {
        this.deactivatedBy = email;
        return self();
    }

    @Override
    public T createdAt(LocalDateTime timestamp) {
        this.createdAt = timestamp;
        return self();
    }

    @Override
    public T updatedAt(LocalDateTime timestamp) {
        this.updatedAt = timestamp;
        return self();
    }

    @Override
    public T deactivatedAt(LocalDateTime timestamp) {
        this.deactivatedAt = timestamp;
        return self();
    }
    
    protected abstract T self();
}
