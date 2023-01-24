package org.kucher.auditservice.dao.entity;

import org.kucher.auditservice.dao.entity.api.IUser;
import org.kucher.auditservice.security.entity.EUserRole;
import org.kucher.auditservice.security.entity.EUserStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@AttributeOverrides({
        @AttributeOverride( name = "uuid", column = @Column(name = "user_uuid")),
        @AttributeOverride( name = "dtCreate", column = @Column(name = "user_dt_create")),
        @AttributeOverride( name = "dtUpdate", column = @Column(name = "user_dt_update")),
        @AttributeOverride( name = "mail", column = @Column(name = "user_mail")),
        @AttributeOverride( name = "nick", column = @Column(name = "user_nick")),
        @AttributeOverride( name = "role", column = @Column(name = "user_role")),
        @AttributeOverride( name = "status", column = @Column(name = "user_status"))
})
public class User implements IUser {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String mail;
    private String nick;
    @Enumerated(EnumType.STRING)
    private EUserRole role;
    @Enumerated(EnumType.STRING)
    private EUserStatus status;

    public User() {
    }

    public User(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String mail, String nick, EUserRole role, EUserStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.nick = nick;
        this.role = role;
        this.status = status;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return this.dtUpdate;
    }


    @Override
    public String getMail() {
        return this.mail;
    }

    @Override
    public String getNick() {
        return this.nick;
    }

    @Override
    public EUserRole getRole() {
        return this.role;
    }

    @Override
    public EUserStatus getStatus() {
        return this.status;
    }
}
