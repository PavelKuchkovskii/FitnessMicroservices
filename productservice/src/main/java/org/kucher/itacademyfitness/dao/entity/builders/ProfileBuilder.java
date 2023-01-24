package org.kucher.itacademyfitness.dao.entity.builders;

import org.kucher.itacademyfitness.dao.entity.Profile;
import org.kucher.itacademyfitness.dao.entity.User;
import org.kucher.itacademyfitness.dao.entity.enums.EActivityType;
import org.kucher.itacademyfitness.dao.entity.enums.ESex;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ProfileBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private int height;
    private double weight;
    private Date dtBirthday;
    private double target;
    private EActivityType activityType;
    private ESex sex;
    private User user;

    private ProfileBuilder() {

    }

    public static ProfileBuilder create() {
        return new ProfileBuilder();
    }

    public ProfileBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ProfileBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public ProfileBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public ProfileBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public ProfileBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public ProfileBuilder setDtBirthday(Date dtBirthday) {
        this.dtBirthday = dtBirthday;
        return this;
    }

    public ProfileBuilder setTarget(double target) {
        this.target = target;
        return this;
    }

    public ProfileBuilder setActivityType(EActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public ProfileBuilder setSex(ESex sex) {
        this.sex = sex;
        return this;
    }

    public ProfileBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public Profile build() {
        return new Profile(uuid, dtCreate, dtUpdate, height, weight, dtBirthday, target, activityType, sex, user);
    }
}
