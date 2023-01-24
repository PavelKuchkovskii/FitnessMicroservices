package org.kucher.userservice.dao.api;

import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.dao.entity.enums.EUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserDao extends JpaRepository<User, UUID> {
    Optional<User> findByNick(String nick);
    Optional<User> findByMail(String mail);
    Optional<List<User>> findAllByStatus(EUserStatus status);
}
