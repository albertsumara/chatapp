package com.example.chatapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final WebClient webClient;

    public WeatherService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public double getTemperatureByCountry(String country) {
        
        Map<String, Object> geoResponse = webClient.get()
                .uri("https://geocoding-api.open-meteo.com/v1/search?name={c}&count=1", country)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,Object>>() {})
                .block();

        if (geoResponse == null || !geoResponse.containsKey("results")) {
            throw new IllegalArgumentException("Country not found");
        }

        List<Map<String,Object>> results = (List<Map<String,Object>>) geoResponse.get("results");
        if (results.isEmpty()) {
            throw new IllegalArgumentException("Country not found");
        }

        double lat = ((Number) results.get(0).get("latitude")).doubleValue();
        double lon = ((Number) results.get(0).get("longitude")).doubleValue();

        Map<String,Object> weatherResponse = webClient.get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true",
                        lat, lon)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,Object>>() {})
                .block();

        Map<String,Object> currentWeather = (Map<String,Object>) weatherResponse.get("current_weather");
        if (currentWeather == null || !currentWeather.containsKey("temperature")) {
            throw new IllegalStateException("No temperature data found");
        }

        return ((Number) currentWeather.get("temperature")).doubleValue();
    }
}
