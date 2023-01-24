package org.kucher.reportservice.dao.api;

import org.kucher.reportservice.dao.entity.BinaryReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBinaryReportDao extends JpaRepository<BinaryReport, Integer> {

    BinaryReport findByReport_Uuid(UUID uuid);
}
