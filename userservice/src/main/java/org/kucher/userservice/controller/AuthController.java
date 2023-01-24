package org.kucher.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kucher.userservice.config.exceptions.api.BadCredentialsException;
import org.kucher.userservice.config.exceptions.api.UserNotActivatedException;
import org.kucher.userservice.dao.entity.enums.EUserStatus;
import org.kucher.userservice.security.utils.JwtTokenUtil;
import org.kucher.userservice.service.AuthService;
import org.kucher.userservice.service.dto.UserDTO;
import org.kucher.userservice.service.dto.UserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthController {

    private final AuthService service;
    private final PasswordEncoder encoder;

    public AuthController(AuthService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> doPost(@Valid @RequestBody UserLoginDTO dto) throws JsonProcessingException {

        UserDTO user = this.service.loadByMail(dto.getMail());

        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Bad credentials");
        }

        if(!user.getStatus().equals(EUserStatus.ACTIVATED)) {
            throw new UserNotActivatedException("User not activated");
        }

        return new ResponseEntity<>(JwtTokenUtil.generateAccessToken(user), HttpStatus.OK);
    }


}
