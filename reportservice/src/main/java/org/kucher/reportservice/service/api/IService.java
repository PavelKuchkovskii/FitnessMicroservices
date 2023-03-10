package org.kucher.reportservice.service.api;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IService<DTO, ENTITY>{

    DTO create(DTO dto);
    DTO read(UUID uuid);
    Page<DTO> get(int page, int itemsPerPage);
    List<DTO> get();
    DTO update(UUID uuid, LocalDateTime dtUpdate, DTO dto);
    void delete(UUID uuid, LocalDateTime dtUpdate);
    boolean validate(DTO dto);
    DTO mapToDTO(ENTITY entity);
    ENTITY mapToEntity(DTO dto);
}
