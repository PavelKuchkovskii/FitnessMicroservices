package org.kucher.reportservice.service;

import org.kucher.reportservice.dao.api.IBinaryReportDao;
import org.kucher.reportservice.dao.entity.BinaryReport;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BinaryReportService {

    private final IBinaryReportDao dao;

    public BinaryReportService(IBinaryReportDao dao) {
        this.dao = dao;
    }

    public BinaryReport read(UUID uuid) {
        return dao.findByReport_Uuid(uuid);
    }
}
