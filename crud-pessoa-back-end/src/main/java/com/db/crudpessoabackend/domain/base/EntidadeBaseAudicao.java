package com.db.crudpessoabackend.domain.base;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class EntidadeBaseAudicao extends EntidadeBase {
    private boolean ativo;
    private String criadoPor;
    private String atualizadoPor;
    private String desativadoPor;
    private LocalDateTime criadoAs;
    private LocalDateTime atualizadoAs;
    private LocalDateTime desativadoAs;
}
