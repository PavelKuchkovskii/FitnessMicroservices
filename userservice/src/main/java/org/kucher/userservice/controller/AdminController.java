package org.kucher.userservice.controller;


import org.kucher.userservice.service.AdminUserService;
import org.kucher.userservice.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class AdminController {

    private final AdminUserService service;

    public AdminController(AdminUserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        Page<UserDTO> recipeDTOS = this.service.get(page, size);

        return new ResponseEntity<>(recipeDTOS, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> read(@PathVariable("uuid") UUID uuid) {

        UserDTO read = this.service.read(uuid);

        return new ResponseEntity<>(read, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> doPost(@Valid @RequestBody UserDTO dto) {

        UserDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserDTO> doPut(@PathVariable("uuid") UUID uuid,
                                         @PathVariable("dt_update") String dt_update,
                                         @Valid @RequestBody UserDTO dto) {

        LocalDateTime dtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(dt_update)), ZoneId.of("UTC"));

        UserDTO updated = this.service.update(uuid, dtUpdate, dto);

        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }
}
