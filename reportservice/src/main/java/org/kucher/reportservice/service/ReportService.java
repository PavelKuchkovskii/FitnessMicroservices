package org.kucher.reportservice.service;

import org.kucher.reportservice.audit.AuditService;
import org.kucher.reportservice.audit.dto.AuditDTO;
import org.kucher.reportservice.audit.dto.enums.EEssenceType;
import org.kucher.reportservice.config.exceptions.api.AlreadyChangedException;
import org.kucher.reportservice.config.exceptions.api.NotFoundException;
import org.kucher.reportservice.dao.api.IReportDao;
import org.kucher.reportservice.dao.entity.Report;
import org.kucher.reportservice.dao.entity.User;
import org.kucher.reportservice.dao.entity.builders.ReportBuilder;
import org.kucher.reportservice.dao.entity.enums.EStatus;
import org.kucher.reportservice.security.entity.UserToJwt;
import org.kucher.reportservice.service.api.IReportService;
import org.kucher.reportservice.service.dto.ReportDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService implements IReportService {

    private final IReportDao dao;
    private final ModelMapper mapper;
    private final AuditService auditService;

    public ReportService(IReportDao dao, ModelMapper mapper, AuditService auditService) {
        this.dao = dao;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public ReportDTO create(ReportDTO dto) {
        dto.setUuid(UUID.randomUUID());
        dto.setDtCreate(LocalDateTime.now());
        dto.setDtUpdate(dto.getDtCreate());
        dto.setStatus(EStatus.LOADED);
        dto.setDescription("REPORT");

        //Get user from Security context
        UserToJwt userToJwt = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Create new user for Profile
        User user = new User(userToJwt.getUuid());
        //Add user to Profile
        dto.setUser(user);

        if(validate(dto)) {
            Report report = mapToEntity(dto);
            dao.save(report);

            //Create audit
            AuditDTO audit = new AuditDTO();
            audit.setUser(userToJwt);
            audit.setText("Create Report");
            audit.setType(EEssenceType.USER);
            audit.setId(dto.getUuid().toString());

            auditService.createAudit(audit);
        }

        return read(dto.getUuid());
    }

    @Override
    public ReportDTO read(UUID uuid) {
        Optional<Report> read = dao.findById(uuid);

        if(read.isPresent()) {
            return read.map(this::mapToDTO).orElse(null);
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public Page<ReportDTO> get(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<Report> reports = dao.findAll(pageable);

        return new PageImpl<>(reports.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, reports.getTotalElements());
    }

    @Override
    public List<ReportDTO> get() {
        return this.dao.findAll().stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReportDTO update(UUID uuid, LocalDateTime dtUpdate, ReportDTO dto) {
        ReportDTO read = this.read(uuid);

        if(read.getDtUpdate().isEqual(dtUpdate)) {
            read.setStatus(dto.getStatus());

            if(validate(read)) {
                Report report = mapToEntity(read);
                dao.save(report);
            }

            return read;
        }
        else {
            throw new AlreadyChangedException();
        }
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate) {

    }

    @Override
    public boolean validate(ReportDTO dto) {
        return true;
    }

    @Override
    public ReportDTO mapToDTO(Report report) {
        return mapper.map(report, ReportDTO.class);
    }

    @Override
    public Report mapToEntity(ReportDTO dto) {
        return ReportBuilder
                .create()
                .setUuid(dto.getUuid())
                .setDtCreate(dto.getDtCreate())
                .setDtUpdate(dto.getDtUpdate())
                .setStatus(dto.getStatus())
                .setType(dto.getType())
                .setDescription(dto.getDescription())
                .setParam(dto.getParam())
                .setUser(dto.getUser())
                .build();
    }
}
