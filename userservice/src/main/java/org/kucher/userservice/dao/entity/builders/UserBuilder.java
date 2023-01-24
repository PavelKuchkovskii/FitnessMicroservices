package org.kucher.userservice.dao.entity.builders;

import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.security.entity.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String mail;
    private String nick;
    private String password;
    private EUserRole role;
    private EUserStatus status;

    private UserBuilder() {

    }

    public static UserBuilder create() {
        return new UserBuilder();
    }

    public UserBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public UserBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public UserBuilder setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserBuilder setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(EUserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder setStatus(EUserStatus status) {
        this.status = status;
        return this;
    }

    public User build() {
        return new User(uuid, dtCreate,dtUpdate, mail, nick, password, role, status);
    }
}
