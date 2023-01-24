package org.kucher.itacademyfitness.dao.api;

import org.kucher.itacademyfitness.dao.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProfileDao extends JpaRepository<Profile, UUID> {
    Profile findProfileByUser_Uuid(UUID uuid);
}
