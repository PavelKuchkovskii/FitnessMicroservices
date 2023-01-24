package org.kucher.itacademyfitness.dao.api;

import org.kucher.itacademyfitness.dao.entity.JournalFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IJournalFoodDao extends JpaRepository<JournalFood, UUID> {
    Page<JournalFood> findAllByProfile_Uuid(UUID profile_uuid, Pageable pageable);

    List<JournalFood> findAllByProfile_UuidAndDtSupplyBetween(UUID profile_uuid, LocalDateTime dtSupply, LocalDateTime dtSupply2);
}
