package org.kucher.reportservice.dao.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverrides({
        @AttributeOverride( name = "uuid", column = @Column(name = "user_uuid")),
})
public class User {

    private UUID uuid;

    public User() {
    }

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

}
