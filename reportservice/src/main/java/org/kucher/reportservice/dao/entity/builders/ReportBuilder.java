package org.kucher.reportservice.dao.entity.builders;

import org.kucher.reportservice.dao.entity.Report;
import org.kucher.reportservice.dao.entity.ReportParam;
import org.kucher.reportservice.dao.entity.User;
import org.kucher.reportservice.dao.entity.enums.EReportType;
import org.kucher.reportservice.dao.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportBuilder {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private EStatus status;
    private EReportType type;
    private String description;
    private ReportParam param;
    private User user;

    private ReportBuilder(){

    }

    public static ReportBuilder create() {
        return new ReportBuilder();
    }

    public ReportBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ReportBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public ReportBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public ReportBuilder setStatus(EStatus status) {
        this.status = status;
        return this;
    }

    public ReportBuilder setType(EReportType type) {
        this.type = type;
        return this;
    }

    public ReportBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ReportBuilder setParam(ReportParam param) {
        this.param = param;
        return this;
    }

    public ReportBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public Report build(){
        return new Report(uuid, dtCreate, dtUpdate, status, type, description, param, user);
    }
}
