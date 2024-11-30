package com.example.filmblogproject.interfaceAdapters.controller;

import com.example.filmblogproject.application.service.MovieService;
import com.example.filmblogproject.domain.entity.Movie;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.FilterMovieRequest;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.MovieRequest;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @QueryMapping
    public List<Movie> getAllMovie() throws DataException{
        return movieService.getAllMovie();
    }

    @QueryMapping
    public List<Movie> filterMovie(@Argument FilterMovieRequest filterMovieRequest) throws DataException {
        return movieService.filterMovie(filterMovieRequest);
    }

    @MutationMapping
    public Movie createMovie(@Argument MovieRequest movieRequest) throws DataException {
        return movieService.createMovie(movieRequest);
    }

}
