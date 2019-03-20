package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;

@Controller("foobar")
public class FooController {

    @GetMapping
    public ResponseEntity<?> getFoobar(HttpServletRequest request) {
        System.out.println("Foobar called: " + request.getRequestURL());
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !isValid(authorization)) {
            return ResponseEntity.status(401).build();
        } else {
            String[] array = new String[]{"foo", "bar"};
            return ResponseEntity.of(Optional.of(array[new Random().nextInt(array.length)]));
        }
    }

    private Boolean isValid(String authorization) {
        String accessToken = authorization.replace("Bearer ", "");
        String[] tokenParts = accessToken.split("\\.");
        if (tokenParts.length != 3) {
            return false;
        }
        return true;
    }
}
