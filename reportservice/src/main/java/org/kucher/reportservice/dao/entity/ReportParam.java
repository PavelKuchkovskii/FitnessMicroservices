package org.kucher.reportservice.dao.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Embeddable
@AttributeOverrides({
        @AttributeOverride( name = "from", column = @Column(name = "dt_from")),
        @AttributeOverride( name = "to", column = @Column(name = "dt_to"))
})
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
