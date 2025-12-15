package com.example.chatapp.controller;

import com.example.chatapp.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {


    static public record WeatherDto(
            String country,
            double latitude,
            double longitude,
            double temperature,
            double windSpeed
    ) {}


    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public double getWeather(@RequestParam String country) {
        return weatherService.getTemperatureByCountry(country);
    }
}
