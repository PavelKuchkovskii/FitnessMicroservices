package org.kucher.itacademyfitness.controller;

import org.kucher.itacademyfitness.service.api.IProfileService;
import org.kucher.itacademyfitness.service.dto.ProfileDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final IProfileService service;

    public ProfileController(IProfileService service) {
        this.service = service;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProfileDTO> doPost(@PathVariable("uuid") UUID uuid) {
        ProfileDTO read = this.service.read(uuid);

        return new ResponseEntity<>(read, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProfileDTO> doPost(@Valid @RequestBody ProfileDTO dto) {
        ProfileDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


}
