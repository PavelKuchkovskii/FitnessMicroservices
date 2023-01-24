package org.kucher.auditservice.service;

import org.kucher.auditservice.config.exceptions.api.NotFoundException;
import org.kucher.auditservice.dao.api.IAuditDao;
import org.kucher.auditservice.dao.entity.Audit;
import org.kucher.auditservice.dao.entity.builders.AuditBuilder;
import org.kucher.auditservice.service.api.IAuditService;
import org.kucher.auditservice.service.dto.AuditDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuditService implements IAuditService {

    private final IAuditDao dao;
    private final ModelMapper mapper;

    public AuditService(IAuditDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AuditDTO create(AuditDTO dto) {
        dto.setUuid(UUID.randomUUID());

        if(validate(dto)) {

            Audit audit = mapToEntity(dto);
            dao.save(audit);
        }

        return dto;
    }

    @Override
    public AuditDTO read(UUID uuid) {
        Optional<Audit> read = dao.findById(uuid);

        if(read.isPresent()) {
            return read.map(this::mapToDTO).orElse(null);
        }
        else {
            throw new NotFoundException();
        }

    }

    @Override
    public Page<AuditDTO> get(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<Audit> audits = dao.findAll(pageable);

        return new PageImpl<>(audits.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, audits.getTotalElements());
    }

    @Override
    public boolean validate(AuditDTO dto) {
        return true;
    }

    @Override
    public AuditDTO mapToDTO(Audit audit) {
        return mapper.map(audit, AuditDTO.class);
    }

    @Override
    public Audit mapToEntity(AuditDTO dto) {
        return AuditBuilder
                .create()
                .setUuid(dto.getUuid())
                .setUser(dto.getUser())
                .setText(dto.getText())
                .setType(dto.getType())
                .setId(dto.getId())
                .build();
    }
}
