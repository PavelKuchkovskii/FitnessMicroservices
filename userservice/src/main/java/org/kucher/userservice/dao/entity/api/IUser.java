package org.kucher.userservice.dao.entity.api;

import org.kucher.userservice.security.entity.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;

public interface IUser extends IEssence {

    String getMail();
    String getNick();
    String getPassword();
    EUserRole getRole();
    EUserStatus getStatus();
}
