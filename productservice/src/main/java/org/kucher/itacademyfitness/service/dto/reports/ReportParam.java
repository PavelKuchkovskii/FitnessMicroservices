package org.kucher.itacademyfitness.service.dto.reports;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReportParam {

    @NotNull(message = "Date from cannot be null")
    private Date from;
    @NotNull(message = "Date to cannot be null")
    private Date to;

    public ReportParam() {
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
