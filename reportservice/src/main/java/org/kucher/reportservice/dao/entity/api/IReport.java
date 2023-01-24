package org.kucher.reportservice.dao.entity.api;

import org.kucher.reportservice.dao.entity.ReportParam;
import org.kucher.reportservice.dao.entity.User;
import org.kucher.reportservice.dao.entity.enums.EReportType;
import org.kucher.reportservice.dao.entity.enums.EStatus;

public interface IReport extends IEssence{

    EStatus getStatus();
    EReportType getType();
    String getDescription();
    ReportParam getParam();
    User getUser();
}
