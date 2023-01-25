package org.kucher.auditservice.dao.entity.api;

import org.kucher.auditservice.dao.entity.User;
import org.kucher.auditservice.dao.entity.enums.EEssenceType;

import java.util.UUID;

public interface IAudit {

    UUID getUuid();
    User getUser();
    String getText();
    EEssenceType getType();
    String getId();
}
