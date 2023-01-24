package org.kucher.reportservice.dao.api;

import org.kucher.reportservice.dao.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IReportDao extends JpaRepository<Report, UUID> {
}
