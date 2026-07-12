package com.meridian.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // tells Spring: "the return value of these methods IS the response body" (as JSON)
public class HealthController {

    @GetMapping("/api/health") // maps HTTP GET requests at this path to this method
    public Map<String, String> health() {
        return Map.of("status", "ok", "service", "meridian-backend");
    }

}
