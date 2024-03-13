package com.db.crudpessoabackend.domain.base.interfaces;

import com.db.crudpessoabackend.domain.base.EntidadeBaseAudicao;

import java.time.LocalDateTime;

public interface IEntidadeBaseAudicaoBuilder {
    IEntidadeBaseAudicaoBuilder criadoPor(String email);
    IEntidadeBaseAudicaoBuilder atualizadoPor(String email);
    IEntidadeBaseAudicaoBuilder desativadoPor(String email);
    IEntidadeBaseAudicaoBuilder criadoAs(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder atualizadoAs(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder desativadoAs(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder ativo(boolean active);
    EntidadeBaseAudicao build();
    IEntidadeBaseAudicaoBuilder reset();
}
