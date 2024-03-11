package com.db.crudpessoabackend.domain.base.interfaces;

import com.db.crudpessoabackend.domain.base.BaseEntityAudit;

import java.time.LocalDateTime;

public interface IBaseEntityAuditBuilder {
    IBaseEntityAuditBuilder createdBy(String email);
    IBaseEntityAuditBuilder updatedBy(String email);
    IBaseEntityAuditBuilder deactivatedBy(String email);
    IBaseEntityAuditBuilder createdAt(LocalDateTime timestamp);
    IBaseEntityAuditBuilder updatedAt(LocalDateTime timestamp);
    IBaseEntityAuditBuilder deactivatedAt(LocalDateTime timestamp);
    IBaseEntityAuditBuilder active(boolean active);
    BaseEntityAudit build();
    IBaseEntityAuditBuilder reset();
}
