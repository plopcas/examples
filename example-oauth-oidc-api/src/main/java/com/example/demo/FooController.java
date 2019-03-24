package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Controller("foobar")
public class FooController {

    @GetMapping
    public ResponseEntity<?> getFoobar(HttpServletRequest request) throws IOException {
        System.out.println("Foobar called: " + request.getRequestURL());
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !isValid(authorization)) {
            return ResponseEntity.status(401).build();
        } else {
            String[] array = new String[]{"foo", "bar"};
            return ResponseEntity.of(Optional.of(array[new Random().nextInt(array.length)]));
        }
    }

    private Boolean isValid(String authorization) throws IOException {
        String accessToken = authorization.replace("Bearer ", "");
        String[] tokenParts = accessToken.split("\\.");
        if (tokenParts.length != 3) {
            System.err.println("Not a JWT");
            return false;
        }

        String accessTokenPayload = accessToken.split("\\.")[1];

        byte[] decodedAccessTokenPayloadBytes = Base64.getDecoder().decode(accessTokenPayload);
        String decodedAccessTokenPayload = new String(decodedAccessTokenPayloadBytes);

        HashMap<String, Object> accessTokenClaims = new ObjectMapper().readValue(decodedAccessTokenPayload, HashMap.class);
        Integer expirationDate = (Integer) accessTokenClaims.get("exp");

        LocalDateTime expirationLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(expirationDate)), ZoneId.systemDefault());

        if (LocalDateTime.now().isAfter(expirationLocalDateTime)) {
            System.err.println("Token expired: " + expirationLocalDateTime);
            return false;
        }

        return true;
    }
}
