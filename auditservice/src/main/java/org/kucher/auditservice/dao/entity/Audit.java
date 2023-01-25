package org.kucher.auditservice.dao.entity;

import org.kucher.auditservice.dao.entity.api.IAudit;
import org.kucher.auditservice.dao.entity.enums.EEssenceType;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Audit implements IAudit {

    @Id
    private UUID uuid;
    @Embedded
    private User user;
    @Column(name = "text")
    private String text;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EEssenceType type;
    @Column(name = "es_id")
    private String id;

    public Audit() {
    }

    public Audit(UUID uuid, User user, String text, EEssenceType type, String id) {
        this.uuid = uuid;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public EEssenceType getType() {
        return this.type;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
