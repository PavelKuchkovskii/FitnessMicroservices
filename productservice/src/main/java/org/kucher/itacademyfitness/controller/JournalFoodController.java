package org.kucher.itacademyfitness.controller;

import org.kucher.itacademyfitness.service.ReportService;
import org.kucher.itacademyfitness.service.api.IJournalFoodService;
import org.kucher.itacademyfitness.service.dto.JournalFoodDTO;
import org.kucher.itacademyfitness.service.dto.reports.Report;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class JournalFoodController {

    private final IJournalFoodService service;
    private final ReportService reportService;

    public JournalFoodController(IJournalFoodService service, ReportService reportService) {
        this.service = service;
        this.reportService = reportService;
    }

    @GetMapping("/{uuid_profile}/journal/food")
    public ResponseEntity<Page<JournalFoodDTO>> doGet(@PathVariable("uuid_profile") UUID uuid,
                                                      @RequestParam int page, @RequestParam int size) {

        Page<JournalFoodDTO> journalFoodDTOS = this.service.get(page, size, uuid);

        return new ResponseEntity<>(journalFoodDTOS, HttpStatus.CREATED);
    }

    @PostMapping("/journal/food")
    public ResponseEntity<List<JournalFoodDTO>> doGetReport(@RequestBody @Valid Report report) {

        List<JournalFoodDTO> journalFoodDTOS = this.reportService.get(report);

        return new ResponseEntity<>(journalFoodDTOS, HttpStatus.CREATED);
    }

    @PostMapping("/{uuid_profile}/journal/food")
    public ResponseEntity<JournalFoodDTO> doPost(@PathVariable("uuid_profile") UUID uuid,
                                                 @Valid @RequestBody JournalFoodDTO dto) {
        JournalFoodDTO created = this.service.create(dto, uuid);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

}
