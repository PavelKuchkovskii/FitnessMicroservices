package org.kucher.userservice.service.dto;

import org.kucher.userservice.config.passay.annotation.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCreateDTO {

    @Email(message = "Mail must be valid")
    private String mail;
    @NotBlank(message = "Nick cannot be blank")
    private String nick;

    @Password
    private String password;

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
}
