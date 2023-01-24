package org.kucher.userservice.service;

import org.kucher.userservice.config.exceptions.api.BadCredentialsException;
import org.kucher.userservice.dao.api.IUserDao;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final IUserDao dao;
    private final ModelMapper mapper;

    public AuthService(IUserDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public UserDTO loadByMail(String mail) {
        Optional<User> user = dao.findByMail(mail);

        if(user.isEmpty()) {
            throw new BadCredentialsException("Bad credentials");
        }

        return this.mapToDTO(user.get());
    }

    public UserDTO mapToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }
}
