package org.kucher.auditservice.service.api;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<DTO, ENTITY>{

    DTO create(DTO dto);
    DTO read(UUID uuid);
    Page<DTO> get(int page, int itemsPerPage);
    boolean validate(DTO dto);
    DTO mapToDTO(ENTITY entity);
    ENTITY mapToEntity(DTO dto);
}
