package org.kucher.itacademyfitness.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kucher.itacademyfitness.dao.entity.User;
import org.kucher.itacademyfitness.dao.entity.enums.EActivityType;
import org.kucher.itacademyfitness.dao.entity.enums.ESex;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ProfileDTO {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;

    @Min(value = 1, message = "Height cannot be null")
    private int height;
    @Min(value = 1, message = "Weight cannot be null")
    private double weight;

    @NotNull(message = "Birthday date cannot be null")
    @JsonProperty("dt_birthday")
    private Date dtBirthday;
    @Min(value = 1, message = "Target cannot be null")
    private double target;
    @NotNull(message = "ActivityType cannot be null")
    @JsonProperty("activity_type")
    private EActivityType activityType;
    @NotNull(message = "Sex cannot be null")
    private ESex sex;
    private User user;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDtBirthday() {
        return dtBirthday;
    }

    public void setDtBirthday(Date dtBirthday) {
        this.dtBirthday = dtBirthday;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public EActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(EActivityType activityType) {
        this.activityType = activityType;
    }

    public ESex getSex() {
        return sex;
    }

    public void setSex(ESex sex) {
        this.sex = sex;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
