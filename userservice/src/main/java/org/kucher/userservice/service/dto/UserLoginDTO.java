package org.kucher.userservice.service.dto;

import javax.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank(message = "Mail cannot be blank")
    private String mail;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
