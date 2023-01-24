package org.kucher.auditservice.dao.entity.builders;

import org.kucher.auditservice.dao.entity.Audit;
import org.kucher.auditservice.dao.entity.User;
import org.kucher.auditservice.dao.entity.enums.EEssenceType;

import java.util.UUID;

public class AuditBuilder {

    private UUID uuid;
    private User user;
    private String text;
    private EEssenceType type;
    private String id;

    private AuditBuilder() {
    }

    public static AuditBuilder create() {
        return new AuditBuilder();
    }

    public AuditBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public AuditBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public AuditBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public AuditBuilder setType(EEssenceType type) {
        this.type = type;
        return this;
    }

    public AuditBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public Audit build() {
        return new Audit(uuid, user, text, type, id);
    }
}
