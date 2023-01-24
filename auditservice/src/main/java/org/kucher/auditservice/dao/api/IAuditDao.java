package org.kucher.auditservice.dao.api;

import org.kucher.auditservice.dao.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAuditDao extends JpaRepository<Audit, UUID> {
}
