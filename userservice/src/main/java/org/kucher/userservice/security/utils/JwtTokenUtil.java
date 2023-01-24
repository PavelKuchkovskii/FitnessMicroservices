package org.kucher.userservice.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.*;
import org.kucher.userservice.config.exceptions.api.InvalidConfirmRegistrationTokenException;
import org.kucher.userservice.config.exceptions.api.JwtTokenGenerationException;
import org.kucher.userservice.config.mapper.deserializer.LocalDateTimeDeserializer;
import org.kucher.userservice.config.mapper.serializer.LocalDateTimeSerializer;
import org.kucher.userservice.service.dto.UserDTO;
import org.kucher.userservice.security.entity.UserToJwt;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtTokenUtil {


    private static final String jwtSecret;
    private static final String jwtIssuer;
    private static final ObjectMapper mapper;

    static {
        jwtSecret = "NDQ1ZjAzNjQtMzViZi00MDRjLTljZjQtNjNjYWIyZTU5ZDYw";
        jwtIssuer = "ITAcademy";
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(module);
    }

    public static String generateAccessToken(UserDTO user) {
        Map<String, Object> map = new HashMap<>();

        UserToJwt userToJwt = new UserToJwt(user.getUuid(),
                user.getDtCreate(),
                user.getDtUpdate(),
                user.getMail(),
                user.getNick(),
                user.getRole(),
                user.getStatus());
        try {
            map.put("user", mapper.writeValueAsString(userToJwt));
        } catch (JsonProcessingException e) {
            throw new JwtTokenGenerationException();
        }


        return Jwts.builder()
                .setClaims(map)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public static UserToJwt getUser(String token) throws JsonProcessingException {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return mapper.readValue(claims.get("user").toString(), UserToJwt.class);
    }

    public static Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException |
                 MalformedJwtException ex) {
            throw new InvalidConfirmRegistrationTokenException("Invalid token");
        }
    }
}
