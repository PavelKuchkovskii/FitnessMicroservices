package org.kucher.userservice.service;

import org.kucher.userservice.audit.AuditService;
import org.kucher.userservice.audit.dto.AuditDTO;
import org.kucher.userservice.audit.dto.enums.EEssenceType;
import org.kucher.userservice.dao.api.IUserDao;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.security.entity.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;
import org.kucher.userservice.dao.entity.builders.UserBuilder;
import org.kucher.userservice.service.dto.UserCreateDTO;
import org.kucher.userservice.service.dto.UserDTO;
import org.kucher.userservice.security.entity.UserToJwt;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final IUserDao dao;
    private final ModelMapper mapper;
    private final AuditService auditService;
    private final PasswordEncoder encoder;

    public UserService(IUserDao dao, ModelMapper mapper, AuditService auditService, PasswordEncoder encoder) {
        this.dao = dao;
        this.mapper = mapper;
        this.auditService = auditService;
        this.encoder = encoder;

        User user = UserBuilder
                .create()
                .setUuid(UUID.randomUUID())
                .setDtCreate(LocalDateTime.now())
                .setDtUpdate(LocalDateTime.now())
                .setMail("admin@gmail.com")
                .setNick("Admin")
                .setPassword(encoder.encode("123"))
                .setRole(EUserRole.ADMIN)
                .setStatus(EUserStatus.ACTIVATED)
                .build();

        dao.save(user);
    }
    @Transactional
    public UserDTO create(UserCreateDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.randomUUID());
        userDTO.setDtCreate(LocalDateTime.now());
        userDTO.setDtUpdate(userDTO.getDtCreate());
        userDTO.setMail(dto.getMail());
        userDTO.setNick(dto.getNick());
        userDTO.setPassword(encoder.encode(dto.getPassword()));
        userDTO.setRole(EUserRole.USER);
        userDTO.setStatus(EUserStatus.WAITING_ACTIVATION);

        if(validate(userDTO)) {
            User user = mapToEntity(userDTO);
            dao.save(user);

            //Create audit
            AuditDTO audit = new AuditDTO();
            UserToJwt userToJwt = new UserToJwt(userDTO.getUuid(), userDTO.getDtCreate(), userDTO.getDtUpdate(), userDTO.getMail(), userDTO.getNick(), userDTO.getRole(), userDTO.getStatus());
            audit.setUser(userToJwt);
            audit.setText("Create User");
            audit.setType(EEssenceType.USER);
            audit.setId(userDTO.getUuid().toString());

            auditService.createAudit(audit);
        }

        return this.read(userDTO.getUuid());
    }

    public UserDTO get(Authentication auth) {
        Optional<User> user = dao.findByNick( ((UserToJwt) auth.getPrincipal()).getNick());

        if(user.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return this.mapToDTO(user.get());
    }

    public UserDTO read(UUID uuid) {
        Optional<User> user = dao.findById(uuid);

        if(user.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return this.mapToDTO(user.get());
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
