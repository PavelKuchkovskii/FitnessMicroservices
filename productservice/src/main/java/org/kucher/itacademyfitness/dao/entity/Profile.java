package org.kucher.itacademyfitness.dao.entity;

import org.kucher.itacademyfitness.dao.entity.api.IProfile;
import org.kucher.itacademyfitness.dao.entity.enums.EActivityType;
import org.kucher.itacademyfitness.dao.entity.enums.ESex;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class Profile implements IProfile {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "height")
    private int height;
    @Column(name = "weight")
    private double weight;
    @Column(name = "dt_birthday")
    private Date dtBirthday;
    @Column(name = "target")
    private double target;
    @Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
    private EActivityType activityType;
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private ESex sex;
    @Embedded
    private User user;

    public Profile() {
    }

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public Profile(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, int height, double weight, Date dtBirthday, double target, EActivityType activityType, ESex sex, User user) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.height = height;
        this.weight = weight;
        this.dtBirthday = dtBirthday;
        this.target = target;
        this.activityType = activityType;
        this.sex = sex;
        this.user = user;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public Date getDtBirthday() {
        return dtBirthday;
    }

    @Override
    public double getTarget() {
        return target;
    }

    @Override
    public EActivityType getActivityType() {
        return activityType;
    }

    @Override
    public ESex getSex() {
        return sex;
    }

    @Override
    public User getUser() {
        return user;
    }
}
