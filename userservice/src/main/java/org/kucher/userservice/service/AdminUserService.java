package org.kucher.userservice.service;

import org.kucher.userservice.audit.AuditService;
import org.kucher.userservice.audit.dto.AuditDTO;
import org.kucher.userservice.audit.dto.enums.EEssenceType;
import org.kucher.userservice.config.exceptions.api.AlreadyChangedException;
import org.kucher.userservice.config.exceptions.api.BadCredentialsException;
import org.kucher.userservice.dao.api.IUserDao;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.dao.entity.builders.UserBuilder;
import org.kucher.userservice.security.entity.UserToJwt;
import org.kucher.userservice.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminUserService {

    private final IUserDao dao;
    private final ModelMapper mapper;
    private final AuditService auditService;
    private final PasswordEncoder encoder;

    public AdminUserService(IUserDao dao, ModelMapper mapper, AuditService service, AuditService auditService, PasswordEncoder encoder) {
        this.dao = dao;
        this.mapper = mapper;
        this.auditService = auditService;
        this.encoder = encoder;
    }

    @Transactional
    public UserDTO create(UserDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.randomUUID());
        userDTO.setDtCreate(LocalDateTime.now());
        userDTO.setDtUpdate(userDTO.getDtCreate());
        userDTO.setMail(dto.getMail());
        userDTO.setNick(dto.getNick());
        userDTO.setPassword(encoder.encode(dto.getPassword()));
        userDTO.setRole(dto.getRole());
        userDTO.setStatus(dto.getStatus());

        if(validate(userDTO)) {
            User user = mapToEntity(userDTO);
            dao.save(user);

            //Create audit
            AuditDTO audit = new AuditDTO();
            UserToJwt userToJwt = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            audit.setUser(userToJwt);
            audit.setText("Create User");
            audit.setType(EEssenceType.USER);
            audit.setId(dto.getUuid().toString());

            auditService.createAudit(audit);
        }

        return this.read(userDTO.getUuid());
    }

    public UserDTO read(UUID uuid) {
        Optional<User> user = dao.findById(uuid);

        if(user.isEmpty()) {
            throw new BadCredentialsException("Bad credentials");
        }

        return this.mapToDTO(user.get());
    }

    public Page<UserDTO> get(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        Page<User> users = dao.findAll(pageable);

        return new PageImpl<>(users.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, users.getTotalElements());
    }
    @Transactional
    public UserDTO update(UUID uuid, LocalDateTime dtUpdate, UserDTO dto) {

        UserDTO read = read(uuid);

        if(read.getDtUpdate().isEqual(dtUpdate)) {
            read.setDtUpdate(LocalDateTime.now());
            read.setMail(dto.getMail());
            read.setNick(dto.getNick());
            read.setPassword(encoder.encode(dto.getPassword()));
            read.setRole(dto.getRole());
            read.setStatus(dto.getStatus());

            if(validate(read)) {
                User user = mapToEntity(read);
                dao.save(user);

                //Create audit
                AuditDTO audit = new AuditDTO();
                UserToJwt userToJwt = (UserToJwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                audit.setUser(userToJwt);
                audit.setText("Update User");
                audit.setType(EEssenceType.USER);
                audit.setId(dto.getUuid().toString());

                auditService.createAudit(audit);
            }

            return read(read.getUuid());
        }
        else {
            throw new AlreadyChangedException();
        }
    }

    public boolean validate(UserDTO dto) {
        if (dto.getUuid() == null) {
            throw new IllegalArgumentException("Uuid cannot be null");
        }
        else if(dto.getDtCreate() == null) {
            throw new IllegalArgumentException("Create date cannot be null");
        }
        else if(dto.getDtUpdate() == null) {
            throw new IllegalArgumentException("Update date cannot be null");
        }
        else if(dto.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        else if(dto.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return true;
    }

    public UserDTO mapToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public User mapToEntity(UserDTO userDTO) {
        return UserBuilder
                .create()
                .setUuid(userDTO.getUuid())
                .setDtCreate(userDTO.getDtCreate())
                .setDtUpdate(userDTO.getDtUpdate())
                .setMail(userDTO.getMail())
                .setNick(userDTO.getNick())
                .setPassword(userDTO.getPassword())
                .setRole(userDTO.getRole())
                .setStatus(userDTO.getStatus())
                .build();
    }
}
