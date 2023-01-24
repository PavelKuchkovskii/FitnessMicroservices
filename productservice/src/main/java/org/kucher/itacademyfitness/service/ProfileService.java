package org.kucher.itacademyfitness.service;

import org.kucher.itacademyfitness.audit.AuditService;
import org.kucher.itacademyfitness.audit.dto.AuditDTO;
import org.kucher.itacademyfitness.audit.dto.enums.EEssenceType;
import org.kucher.itacademyfitness.config.exceptions.api.AlreadyChangedException;
import org.kucher.itacademyfitness.config.exceptions.api.NotFoundException;
import org.kucher.itacademyfitness.dao.api.IProfileDao;
import org.kucher.itacademyfitness.dao.entity.Profile;
import org.kucher.itacademyfitness.dao.entity.User;
import org.kucher.itacademyfitness.dao.entity.builders.ProfileBuilder;
import org.kucher.itacademyfitness.security.entity.UserToJwt;
import org.kucher.itacademyfitness.service.api.IProfileService;
import org.kucher.itacademyfitness.service.dto.ProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProfileService implements IProfileService {

    private final IProfileDao dao;
    private final ModelMapper mapper;
    private final AuditService auditService;

    public ProfileService(IProfileDao dao, ModelMapper mapper, AuditService auditService) {
        this.dao = dao;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public ProfileDTO create(ProfileDTO dto) {
        dto.setUuid(UUID.randomUUID());
        dto.setDtCreate(LocalDateTime.now());
        dto.setDtUpdate(dto.getDtCreate());

        //USER HOLDER!!!

        //Get user from Security context
        UserToJwt userToJwt = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Create new user for Profile
        User user = new User(userToJwt.getUuid(), userToJwt.getDtCreate(), userToJwt. getDtUpdate());
        //Add user to Profile
        dto.setUser(user);

        if(validate(dto)) {
            Profile profile = mapToEntity(dto);
            dao.save(profile);

            //Create audit
            AuditDTO audit = new AuditDTO();
            audit.setUser(userToJwt);
            audit.setText("Create Profile");
            audit.setType(EEssenceType.PROFILE);
            audit.setId(dto.getUuid().toString());

            auditService.createAudit(audit);
        }

        return dto;
    }

    @Override
    public ProfileDTO read(UUID uuid) {
        Optional<Profile> profile = dao.findById(uuid);

        if(profile.isPresent()) {
            return this.mapToDTO(profile.get());
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public Page<ProfileDTO> get(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<Profile> profiles = dao.findAll(pageable);

        return new PageImpl<>(profiles.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, profiles.getTotalElements());
    }

    @Override
    @Transactional
    public ProfileDTO update(UUID uuid, LocalDateTime dtUpdate, ProfileDTO dto) {
        ProfileDTO read = read(uuid);

        if(dtUpdate.isEqual(read.getDtUpdate())) {
            read.setDtUpdate(LocalDateTime.now());
            read.setHeight(dto.getHeight());
            read.setWeight(dto.getWeight());
            read.setDtBirthday(dto.getDtBirthday());
            read.setTarget(dto.getTarget());
            read.setActivityType(dto.getActivityType());
            read.setSex(dto.getSex());

            if(validate(read)) {
                Profile profile = this.mapToEntity(read);
                dao.save(profile);

                //Create audit
                AuditDTO audit = new AuditDTO();
                UserToJwt user = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                audit.setUser(user);
                audit.setText("Update Profile");
                audit.setType(EEssenceType.PROFILE);
                audit.setId(dto.getUuid().toString());

                auditService.createAudit(audit);

            }
        }
        else {
            throw new AlreadyChangedException();
        }

        return read;
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate) {
        ProfileDTO read = read(uuid);
        if(dtUpdate.isEqual(read.getDtUpdate())) {
            dao.delete(mapToEntity(read));

            //Create audit
            AuditDTO audit = new AuditDTO();
            UserToJwt user = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            audit.setUser(user);
            audit.setText("Delete Profile");
            audit.setType(EEssenceType.PROFILE);
            audit.setId(read.getUuid().toString());

            auditService.createAudit(audit);
        }
        else {
            throw new AlreadyChangedException();
        }
    }

    @Override
    public boolean validate(ProfileDTO dto) {
        if(dto.getUuid() == null) {
            throw new IllegalArgumentException("Uuid cannot be null");
        }
        else if(dto.getDtCreate() == null){
            throw new IllegalArgumentException("Date create cannot be null");
        }
        else if(dto.getDtUpdate()== null){
            throw new IllegalArgumentException("Date update cannot be null");
        }
        else if(dto.getUser() == null || dto.getUser().getUuid() == null || dto.getUser().getDtCreate() == null || dto.getUser().getDtUpdate() == null) {
            throw new IllegalArgumentException("Something wrong with user");
        }

        return true;
    }

    @Override
    public ProfileDTO mapToDTO(Profile profile) {
        return mapper.map(profile, ProfileDTO.class);
    }

    @Override
    public Profile mapToEntity(ProfileDTO dto) {
        return ProfileBuilder
                .create()
                .setUuid(dto.getUuid())
                .setDtCreate(dto.getDtCreate())
                .setDtUpdate(dto.getDtUpdate())
                .setHeight(dto.getHeight())
                .setWeight(dto.getWeight())
                .setDtBirthday(dto.getDtBirthday())
                .setTarget(dto.getTarget())
                .setActivityType(dto.getActivityType())
                .setSex(dto.getSex())
                .setUser(dto.getUser())
                .build();
    }
}
