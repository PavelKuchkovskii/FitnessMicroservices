package org.kucher.reportservice.service.dto;

import org.kucher.reportservice.dao.entity.ReportParam;
import org.kucher.reportservice.dao.entity.User;
import org.kucher.reportservice.dao.entity.enums.EReportType;
import org.kucher.reportservice.dao.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportDTO {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private EStatus status;
    private EReportType type;
    private String description;
    private ReportParam param;
    private User user;

    public ReportDTO() {
    }

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

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public EReportType getType() {
        return type;
    }

    public void setType(EReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportParam getParam() {
        return param;
    }

    public void setParam(ReportParam param) {
        this.param = param;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
