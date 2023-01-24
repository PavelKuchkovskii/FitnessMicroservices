package org.kucher.itacademyfitness.dao.entity;

import org.kucher.itacademyfitness.dao.entity.api.IUser;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@AttributeOverrides({
        @AttributeOverride( name = "uuid", column = @Column(name = "user_uuid")),
        @AttributeOverride( name = "dtCreate", column = @Column(name = "user_dt_create")),
        @AttributeOverride( name = "dtUpdate", column = @Column(name = "user_dt_update"))
})
public class User implements IUser {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;

    public User() {
    }

    public User(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return this.dtUpdate;
    }
}
