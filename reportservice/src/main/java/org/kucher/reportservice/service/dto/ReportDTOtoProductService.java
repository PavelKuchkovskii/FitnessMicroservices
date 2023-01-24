package org.kucher.reportservice.service.dto;

import org.kucher.reportservice.dao.entity.ReportParam;
import org.kucher.reportservice.dao.entity.User;

public class ReportDTOtoProductService {

    private ReportParam param;
    private User user;

    public ReportDTOtoProductService() {
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
