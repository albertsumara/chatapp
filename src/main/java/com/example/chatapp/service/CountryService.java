package com.example.chatapp.service;

import com.example.chatapp.dto.CountryDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final WebClient webClient;

    public CountryService() {
        this.webClient = WebClient.create("https://restcountries.com/v3.1/all?fields=name,cca2,cca3,flags");
    }

    public List<Map<String, Object>> getAllCountries() {
        try {
            return webClient.get()
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Błąd WebClient: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }

    public List<CountryDto> getAllDtoCountries() {

        AtomicLong counter = new AtomicLong(1);

        return getAllCountries().stream()
                .map(c -> {
                    Map<String, Object> name =
                            (Map<String, Object>) c.get("name");
                    Map<String, Object> flags =
                            (Map<String, Object>) c.get("flags");

                    String countryName = (String) name.get("common");
                    String flagImg = (String) flags.get("png");

                    return new CountryDto(
                            counter.getAndIncrement(),
                            countryName,
                            flagImg
                    );
                })
                .collect(Collectors.toList());
    }

    public CountryDto getCountryById(Long countryId) {
        return getAllDtoCountries().stream()
                .filter(c -> c.id().equals(countryId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Country not found for id=" + countryId)
                );
    }
}
