package org.kucher.reportservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.kucher.reportservice.config.utils.excel.ExcelCreator;
import org.kucher.reportservice.config.utils.mapper.deserializer.LocalDateTimeDeserializer;
import org.kucher.reportservice.config.utils.mapper.serializer.LocalDateTimeSerializer;
import org.kucher.reportservice.dao.api.IBinaryReportDao;
import org.kucher.reportservice.dao.entity.BinaryReport;
import org.kucher.reportservice.dao.entity.enums.EStatus;
import org.kucher.reportservice.security.entity.EUserRole;
import org.kucher.reportservice.security.entity.EUserStatus;
import org.kucher.reportservice.security.entity.UserToJwt;
import org.kucher.reportservice.security.utils.JwtTokenUtil;
import org.kucher.reportservice.service.api.IReportService;
import org.kucher.reportservice.service.dto.JournalFoodDTO;
import org.kucher.reportservice.service.dto.ReportDTO;
import org.kucher.reportservice.service.dto.ReportDTOtoProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReportCreatorService {

    private static final String URI = "http://159.223.153.65:8080/api/v1/profile/journal/food";

    private final IBinaryReportDao dao;
    private final IReportService service;
    private final ObjectMapper mapper;

    private final ExcelCreator creator;

    public ReportCreatorService(IBinaryReportDao dao, IReportService service, ExcelCreator creator) {
        this.dao = dao;
        this.service = service;
        this.creator = creator;
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(module);
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void scheduleCheckReports() {
        List<ReportDTO> reports = service.get();

        for (ReportDTO report : reports) {
            if(report.getStatus().equals(EStatus.LOADED)) {
                report.setStatus(EStatus.PROGRESS);
                service.update(report.getUuid(), report.getDtUpdate(), report);

                //Create xlsx
                try {
                    byte[] array = creator.createBook(getJournalFood(report));
                    BinaryReport binaryReport = new BinaryReport(array, service.mapToEntity(report));
                    dao.save(binaryReport);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                finally {
                    report.setStatus(EStatus.ERROR);
                    service.update(report.getUuid(), report.getDtUpdate(), report);
                }


                report.setStatus(EStatus.DONE);
                service.update(report.getUuid(), report.getDtUpdate(), report);

            }
        }
    }

    public List<JournalFoodDTO> getJournalFood(ReportDTO report) throws IOException, InterruptedException, URISyntaxException {
        ReportDTOtoProductService repDto = new ReportDTOtoProductService();
        repDto.setParam(report.getParam());
        repDto.setUser(report.getUser());

        String body = mapper.writeValueAsString(repDto);

        UserToJwt user = new UserToJwt(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "report.service", "report.service", EUserRole.SERVICE, EUserStatus.ACTIVATED);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .headers("Content-Type", "application/json;charset=UTF-8")
                .headers("Authorization", "Bearer " + JwtTokenUtil.generateAccessToken(user))
                .build();

        System.out.println(JwtTokenUtil.generateAccessToken(user));

        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        return mapper.readValue(response.body(), new TypeReference<List<JournalFoodDTO>>(){});
    }
}
