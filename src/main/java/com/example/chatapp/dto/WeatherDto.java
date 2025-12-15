package com.example.chatapp.dto;


public record WeatherDto(
        String country,
        double latitude,
        double longitude,
        double temperature,
        double windSpeed
) {}