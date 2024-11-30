package com.example.filmblogproject.interfaceAdapters.dto.request.service;

import lombok.Data;

@Data
public class FilterMovieRequest {
    private Long id;
    private String releaseYear;
    private String countryCode;
    private String filterName;
}
