package com.example.filmblogproject.interfaceAdapters.dto.request.service;

import com.example.filmblogproject.application.service.ActorService;
import com.example.filmblogproject.application.service.CategoryService;
import com.example.filmblogproject.application.service.DirectorService;
import com.example.filmblogproject.domain.repository.MovieRepository;
import com.example.filmblogproject.infrastructure.repositoryImpl.MovieJdbcRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Data
public class MovieServiceRequest {
    private final MovieRepository movieRepository;
    private final MovieJdbcRepository jdbcRepository;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final CategoryService categoryService;
}
