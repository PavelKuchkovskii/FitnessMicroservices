package org.kucher.itacademyfitness.controller;

import org.kucher.itacademyfitness.service.api.IProductService;
import org.kucher.itacademyfitness.service.dto.ProductDTO;
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
@RequestMapping("/product")
public class ProductController {

    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<Page<ProductDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        Page<ProductDTO> productDTOS = this.service.get(page, size);

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ProductDTO> doPost(@Valid @RequestBody ProductDTO dto) {

        ProductDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ProductDTO> doPut(@PathVariable("uuid") UUID uuid,
                                            @PathVariable("dt_update") String dt_update,
                                            @Valid @RequestBody ProductDTO dto) {

        LocalDateTime dtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(dt_update)), ZoneId.of("UTC"));

        ProductDTO updated = this.service.update(uuid, dtUpdate, dto);

        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

}
