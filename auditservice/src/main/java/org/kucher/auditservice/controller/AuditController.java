package org.kucher.auditservice.controller;

import org.kucher.auditservice.service.api.IAuditService;
import org.kucher.auditservice.service.dto.AuditDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final IAuditService service;

    public AuditController(IAuditService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<AuditDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        Page<AuditDTO> auditDTOS = this.service.get(page, size);

        return new ResponseEntity<>(auditDTOS, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AuditDTO> doRead(@PathVariable("uuid") UUID uuid) {

        AuditDTO auditDTO = this.service.read(uuid);

        return new ResponseEntity<>(auditDTO, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<AuditDTO> doPost(@Valid @RequestBody AuditDTO dto) {

        AuditDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
