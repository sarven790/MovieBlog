package com.example.filmblogproject.interfaceAdapters.controller;

import com.example.filmblogproject.application.service.CountryService;
import com.example.filmblogproject.domain.entity.Country;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.CountryRequest;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @QueryMapping
    public List<Country> getAllCountry() throws DataException {
        return countryService.getAllCountry();
    }

    @MutationMapping
    public Country createCountry(@Argument CountryRequest countryRequest) throws DataException {
        return countryService.createCountry(countryRequest);
    }

}
