package org.kucher.reportservice.dao.entity;

import org.kucher.reportservice.dao.entity.api.IReport;
import org.kucher.reportservice.dao.entity.enums.EReportType;
import org.kucher.reportservice.dao.entity.enums.EStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Report implements IReport {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EReportType type;
    @Column(name = "description")
    private String description;
    @Embedded
    private ReportParam param;
    @Embedded
    private User user;

    public Report() {
    }

    public Report(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, EStatus status, EReportType type, String description, ReportParam param, User user) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.param = param;
        this.user = user;
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

    @Override
    public EStatus getStatus() {
        return this.status;
    }

    @Override
    public EReportType getType() {
        return this.type;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public ReportParam getParam() {
        return this.param;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
