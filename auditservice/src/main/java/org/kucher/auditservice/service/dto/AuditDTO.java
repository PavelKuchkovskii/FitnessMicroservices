package org.kucher.auditservice.service.dto;

import org.kucher.auditservice.dao.entity.User;
import org.kucher.auditservice.dao.entity.enums.EEssenceType;

import java.util.UUID;

public class AuditDTO {

    private UUID uuid;
    private User user;
    private String text;
    private EEssenceType type;
    private String id;

    public AuditDTO() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EEssenceType getType() {
        return type;
    }

    public void setType(EEssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
