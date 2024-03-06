package com.db.crudpessoabackend.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper=false)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntityAudit extends BaseEntity {

    private String createdBy;
    private String updatedBy;
    private String deactivatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deactivatedAt;
}
