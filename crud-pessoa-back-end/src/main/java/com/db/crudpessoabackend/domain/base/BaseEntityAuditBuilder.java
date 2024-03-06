package com.db.crudpessoabackend.domain.base;

import com.db.crudpessoabackend.domain.base.interfaces.IBaseEntityAuditBuilder;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntityAuditBuilder implements IBaseEntityAuditBuilder{

    private String createdBy;
    private String updatedBy;
    private String deactivatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deactivatedAt;
    
    @Override
    public IBaseEntityAuditBuilder createdBy(String email) {
        this.createdBy = email;
        return this;
    }

    @Override
    public IBaseEntityAuditBuilder updatedBy(String email) {
        this.updatedBy = email;
        return this;
    }

    @Override
    public IBaseEntityAuditBuilder deactivatedBy(String email) {
        this.deactivatedBy = email;
        return this;
    }

    @Override
    public IBaseEntityAuditBuilder createdAt(LocalDateTime timestamp) {
        this.createdAt = timestamp;
        return this;
    }

    @Override
    public IBaseEntityAuditBuilder updatedAt(LocalDateTime timestamp) {
        this.updatedAt = timestamp;
        return this;
    }

    @Override
    public IBaseEntityAuditBuilder deactivatedAt(LocalDateTime timestamp) {
        this.deactivatedAt = timestamp;
        return this;
    }
    
}
