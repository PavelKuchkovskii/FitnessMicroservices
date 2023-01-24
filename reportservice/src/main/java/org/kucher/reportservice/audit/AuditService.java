package org.kucher.reportservice.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.kucher.reportservice.audit.dto.AuditDTO;
import org.kucher.reportservice.config.utils.mapper.deserializer.LocalDateTimeDeserializer;
import org.kucher.reportservice.config.utils.mapper.serializer.LocalDateTimeSerializer;
import org.kucher.reportservice.security.entity.EUserRole;
import org.kucher.reportservice.security.entity.EUserStatus;
import org.kucher.reportservice.security.entity.UserToJwt;
import org.kucher.reportservice.security.utils.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuditService {

    private static final String URI = "http://159.223.153.65:8083/api/v1/audit/add";

    private final ObjectMapper mapper;

    public AuditService() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(module);
    }

    public void createAudit(AuditDTO dto) {

        try {
            String body = mapper.writeValueAsString(dto);

            UserToJwt user = new UserToJwt(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "report.service", "report.service", EUserRole.SERVICE, EUserStatus.ACTIVATED);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URI))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .headers("Authorization", "Bearer " + JwtTokenUtil.generateAccessToken(user))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }


    }
}
