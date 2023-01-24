package org.kucher.reportservice.controller;

import org.apache.poi.util.IOUtils;
import org.kucher.reportservice.dao.entity.ReportParam;
import org.kucher.reportservice.dao.entity.enums.EReportType;
import org.kucher.reportservice.service.BinaryReportService;
import org.kucher.reportservice.service.api.IReportService;
import org.kucher.reportservice.service.dto.ReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@RestController
@RequestMapping("/report")
public class ReportController {

    private final IReportService service;
    private final BinaryReportService binaryReportService;

    public ReportController(IReportService service, BinaryReportService binaryReportService) {
        this.service = service;
        this.binaryReportService = binaryReportService;
    }

    @GetMapping
    public ResponseEntity<Page<ReportDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        Page<ReportDTO> reportDTOS = this.service.get(page, size);

        return new ResponseEntity<>(reportDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/export", method = RequestMethod.GET)
    public @ResponseBody byte[] export(@PathVariable UUID uuid, HttpServletResponse response) {
        byte[] output = binaryReportService.read(uuid).getRepArray();

        try (InputStream data = new ByteArrayInputStream(output)) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + uuid.toString() + ".xlsx");
            return IOUtils.toByteArray(data);
        } catch (IOException e) {
            throw new RuntimeException("Error during xlsx export");
        }
    }

    @PostMapping("/{type}")
    public ResponseEntity<ReportDTO> doPost(@PathVariable("type") EReportType type,
                                            @Valid @RequestBody ReportParam param) {

        ReportDTO dto = new ReportDTO();
        dto.setParam(param);
        dto.setType(type);

        ReportDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
