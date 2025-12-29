package com.example.chatapp.controller;

import com.example.chatapp.dto.CountryDto;
import com.example.chatapp.service.CountryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
         this.countryService = countryService;
    }


    @GetMapping
    public List<CountryDto> getCountries() {
        return countryService.getAllDtoCountries();
    }

    @PostMapping("/select")
    public ResponseEntity<Void> selectCountry(@RequestBody Long countryId, HttpSession session) {

        session.setAttribute("countryId", countryId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/selected")
    public ResponseEntity<CountryDto> getSelectedCountry(HttpSession session) {

        Long id = (Long) session.getAttribute("countryId");

        if( id == null ){
            id = 141L;
        }

        CountryDto dto = countryService.getCountryById(id);
        return ResponseEntity.ok(dto);


    }

}
