package org.kucher.itacademyfitness.controller;

import org.kucher.itacademyfitness.service.api.IRecipeService;
import org.kucher.itacademyfitness.service.dto.RecipeDTO;
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
@RequestMapping("/recipe")
public class RecipeController {

    private IRecipeService service;

    public RecipeController(IRecipeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<RecipeDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        Page<RecipeDTO> recipeDTOS = this.service.get(page, size);

        return new ResponseEntity<>(recipeDTOS, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> doPost(@Valid @RequestBody RecipeDTO dto) {
        RecipeDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<RecipeDTO> doPut(@PathVariable("uuid") UUID uuid,
                                            @PathVariable("dt_update") String dt_update,
                                            @Valid @RequestBody RecipeDTO dto) {

        LocalDateTime dtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(dt_update)), ZoneId.of("UTC"));

        RecipeDTO updated = this.service.update(uuid, dtUpdate, dto);

        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }


}
