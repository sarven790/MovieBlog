package com.example.filmblogproject.interfaceAdapters.dto.request.service;

import lombok.Data;

import java.util.List;

@Data
public class MovieRequest {

    private String title;
    private String detail;
    private Long directorId;
    private List<Long> actorIdList;
    private List<Long> categoryIdList;
    private String releaseYear;
    private String countryCode;
    private String imdb;

}
