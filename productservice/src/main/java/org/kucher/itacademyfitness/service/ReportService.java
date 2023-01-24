package org.kucher.itacademyfitness.service;

import org.kucher.itacademyfitness.dao.api.IJournalFoodDao;
import org.kucher.itacademyfitness.dao.api.IProfileDao;
import org.kucher.itacademyfitness.dao.entity.JournalFood;
import org.kucher.itacademyfitness.dao.entity.Profile;
import org.kucher.itacademyfitness.service.dto.JournalFoodDTO;
import org.kucher.itacademyfitness.service.dto.reports.Report;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final IJournalFoodDao journalFoodDao;
    private final IProfileDao profileDao;
    private final ModelMapper mapper;

    public ReportService(IJournalFoodDao journalFoodDao, IProfileDao profileDao, ModelMapper mapper) {
        this.journalFoodDao = journalFoodDao;
        this.profileDao = profileDao;
        this.mapper = mapper;
    }

    public List<JournalFoodDTO> get(Report report) {
        Profile profile = profileDao.findProfileByUser_Uuid(report.getUser().getUuid());
        List<JournalFood> journalFoods = journalFoodDao
                .findAllByProfile_UuidAndDtSupplyBetween(profile.getUuid(),
                        report.getParam().getFrom().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime(),
                        report.getParam().getTo().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime());


        return journalFoods.stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public JournalFoodDTO mapToDTO(JournalFood journalFood) {
        return mapper.map(journalFood, JournalFoodDTO.class);
    }
}
