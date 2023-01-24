package org.kucher.userservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kucher.userservice.config.passay.annotation.Password;
import org.kucher.userservice.security.entity.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserDTO {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    @Email
    private String mail;
    @NotBlank(message = "Nick cannot be blank")
    private String nick;
    @JsonIgnore
    @Password
    private String password;
    @NotNull(message = "Nick cannot be null")
    private EUserRole role;
    @NotNull(message = "Nick cannot be null")
    private EUserStatus status;

    public UserDTO() {
    }

    public UserDTO(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, @Email String mail, @NotBlank(message = "Nick cannot be blank") String nick, @NotNull(message = "Nick cannot be null") EUserRole role, @NotNull(message = "Nick cannot be null") EUserStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.nick = nick;
        this.role = role;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }
}
