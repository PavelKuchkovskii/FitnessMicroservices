package org.kucher.itacademyfitness.service.dto.reports;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Report {

    @Valid
    @NotNull(message = "Report params cannot be null")
    private ReportParam param;
    @Valid
    @NotNull(message = "User params cannot be null")
    private UserFromReportDTO user;

    public Report() {
    }

    public ReportParam getParam() {
        return param;
    }

    public void setParam(ReportParam param) {
        this.param = param;
    }

    public UserFromReportDTO getUser() {
        return user;
    }

    public void setUser(UserFromReportDTO user) {
        this.user = user;
    }
}
