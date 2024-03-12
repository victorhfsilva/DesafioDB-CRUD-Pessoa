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
    private boolean active;
    private String createdBy;
    private String updatedBy;
    private String deactivatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deactivatedAt;
}
