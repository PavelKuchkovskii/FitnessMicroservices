package org.kucher.userservice.service;

import io.jsonwebtoken.*;
import org.kucher.userservice.audit.AuditService;
import org.kucher.userservice.audit.dto.AuditDTO;
import org.kucher.userservice.audit.dto.enums.EEssenceType;
import org.kucher.userservice.config.exceptions.api.AlreadyActivatedException;
import org.kucher.userservice.config.exceptions.api.InvalidConfirmRegistrationTokenException;
import org.kucher.userservice.dao.api.IUserDao;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.dao.entity.builders.UserBuilder;
import org.kucher.userservice.dao.entity.enums.EUserStatus;
import org.kucher.userservice.security.entity.UserToJwt;
import org.kucher.userservice.service.api.IEmailService;
import org.kucher.userservice.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class ConfirmRegistrationService {

    private static final String jwtSecret;
    private static final String jwtIssuer;
    private static final String message;
    private final IUserDao dao;
    private final ModelMapper mapper;
    private final AuditService auditService;

    private final IEmailService service;

    static {
        jwtSecret = "FcNDQ1q1ZjAzNjQtsMz2ViZi1dMDRjLTljZj44QtN12jN123jYWIyU5ZDYw";
        jwtIssuer = "ITAcademy";
        message = "Follow the link to confirm your registration: http://159.223.153.65:8082/api/v1/users/registration/confirm/";
    }

    public ConfirmRegistrationService(IUserDao dao, ModelMapper mapper, AuditService auditService, IEmailService service) {
        this.dao = dao;
        this.mapper = mapper;
        this.auditService = auditService;
        this.service = service;
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void sendingMessages() {
        List<User> users = dao.findAllByStatus(EUserStatus.WAITING_ACTIVATION).get();

        if(!users.isEmpty()) {
            for (User user : users) {
                service.sendSimpleEmail(user.getMail(), "Confirm registration", message + generateAccessToken(user.getUuid()));

                UserDTO userDTO = mapToDTO(user);
                userDTO.setStatus(EUserStatus.WAITING_CONFIRM);

                update(userDTO);
            }
        }
    }
    @Transactional
    public void confirmRegistration(String token) {
        validate(token);
        UserDTO user = read(getUuid(token));

        if(user.getStatus().equals(EUserStatus.WAITING_CONFIRM)) {
            user.setStatus(EUserStatus.ACTIVATED);
            update(user);

            //Create audit
            AuditDTO audit = new AuditDTO();
            UserToJwt userToJwt = new UserToJwt(user.getUuid(), user.getDtCreate(), user.getDtUpdate(), user.getMail(), user.getNick(), user.getRole(), user.getStatus());
            audit.setUser(userToJwt);
            audit.setText("Confirm registration User");
            audit.setType(EEssenceType.USER);
            audit.setId(user.getUuid().toString());

            auditService.createAudit(audit);
        }
        else if (user.getStatus().equals(EUserStatus.ACTIVATED)) {
            throw new AlreadyActivatedException("Already activated");
        }
        else {
            //Тут это скорее всего не то что нужно
            throw new IllegalArgumentException();
        }

    }

    private UserDTO read(UUID uuid) {
        Optional<User> user = dao.findById(uuid);

        if(user.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return this.mapToDTO(user.get());
    }

    private void update(UserDTO dto) {
        dao.save(mapToEntity(dto));
    }

    public String generateAccessToken(UUID uuid) {
        return Jwts.builder()
                .setSubject(uuid.toString())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1))) // 1 HOUR
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private UUID getUuid(String token) {
        return UUID.fromString(Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    private void validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException |
                 MalformedJwtException ex) {
            throw new InvalidConfirmRegistrationTokenException("Invalid token");
        }
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
