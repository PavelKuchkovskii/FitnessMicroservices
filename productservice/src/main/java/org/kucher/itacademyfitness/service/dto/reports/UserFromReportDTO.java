package org.kucher.itacademyfitness.service.dto.reports;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UserFromReportDTO {

    @NotNull(message = "User uuid cannot be null")
    private UUID uuid;

    public UserFromReportDTO() {
    }

    public UserFromReportDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
