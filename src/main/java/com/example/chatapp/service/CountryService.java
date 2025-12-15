package com.example.chatapp.service;

import com.example.chatapp.dto.CountryDto;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;



public class CountryService {

    private final WebClient webClient;

    public CountryService (WebClient.Builder webClientBuilder){

        this.webClient = webClientBuilder.baseUrl("https://restcountries.com/v3.1").build();

    }

    public List<Map<String, Object>> getAllCountries() {

        return webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToMono(List.class)
                .block();

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

}
