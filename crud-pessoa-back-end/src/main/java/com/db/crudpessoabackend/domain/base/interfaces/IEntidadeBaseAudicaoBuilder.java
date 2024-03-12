package com.db.crudpessoabackend.domain.base.interfaces;

import com.db.crudpessoabackend.domain.base.EntidadeBaseAudicao;

import java.time.LocalDateTime;

public interface IEntidadeBaseAudicaoBuilder {
    IEntidadeBaseAudicaoBuilder createdBy(String email);
    IEntidadeBaseAudicaoBuilder updatedBy(String email);
    IEntidadeBaseAudicaoBuilder deactivatedBy(String email);
    IEntidadeBaseAudicaoBuilder createdAt(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder updatedAt(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder deactivatedAt(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder active(boolean active);
    EntidadeBaseAudicao build();
    IEntidadeBaseAudicaoBuilder reset();
}
