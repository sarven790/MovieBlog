package com.example.filmblogproject.application.service;

import com.example.filmblogproject.domain.entity.Country;
import com.example.filmblogproject.domain.repository.CountryRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.CountryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<Country> getAllCountry() {
        return countryRepository.findAll();
    }

    public Country createCountry(CountryRequest request) {
        Country country = new Country();
        country.setCountryCode(request.getCountryCode());
        country.setCountryName(request.getCountryName());
        return countryRepository.save(country);
    }

}
