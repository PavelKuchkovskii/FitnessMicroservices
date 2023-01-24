package org.kucher.auditservice.dao.entity.api;

import org.kucher.auditservice.security.entity.EUserRole;
import org.kucher.auditservice.security.entity.EUserStatus;

public interface IUser extends IEssence {

    String getMail();
    String getNick();
    EUserRole getRole();
    EUserStatus getStatus();
}
