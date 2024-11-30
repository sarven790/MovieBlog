package com.example.filmblogproject.application.service;

import com.example.filmblogproject.domain.entity.Actor;
import com.example.filmblogproject.domain.entity.Category;
import com.example.filmblogproject.domain.entity.Director;
import com.example.filmblogproject.domain.entity.Movie;
import com.example.filmblogproject.interfaceAdapters.dto.request.repository.RepositoryRequestByLong;
import com.example.filmblogproject.interfaceAdapters.dto.request.repository.RepositoryRequestByString;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.FilterMovieRequest;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.MovieRequest;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.MovieServiceRequest;
import com.example.filmblogproject.interfaceAdapters.handler.BusinessException;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.RequiredArgsConstructor;
import org.hibernate.TransientObjectException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieServiceRequest movieServiceRequest;

    public List<Movie> getAllMovie() {
        return movieServiceRequest.getMovieRepository().findAll();
    }

    public List<Movie> filterMovie(FilterMovieRequest request) {

        List<Movie> responseList = new ArrayList<>();

        if (request.getFilterName().equals("1")) {
            responseList = movieServiceRequest.getMovieRepository().getMovieByDirectorId(request.getId());
        } else if (request.getFilterName().equals("2")) {
            responseList = movieServiceRequest.getJdbcRepository().getMovieByActorId(new RepositoryRequestByLong(request.getId()));
        } else if (request.getFilterName().equals("3")) {
            responseList = movieServiceRequest.getJdbcRepository().getMovieByReleaseYear(new RepositoryRequestByString(request.getReleaseYear()));
        }else if (request.getFilterName().equals("4")) {
            responseList = movieServiceRequest.getJdbcRepository().getMovieByDirectorId(new RepositoryRequestByLong(request.getId()));
        }else if (request.getFilterName().equals("5")) {
            responseList = movieServiceRequest.getJdbcRepository().getMovieByCountryCode(new RepositoryRequestByString(request.getCountryCode()));
        }
        return responseList;
    }

    public Movie createMovie(MovieRequest request) throws DataException {
        Movie response;
        try {
            Movie movie = new Movie();
            movie.setTitle(request.getTitle());
            movie.setDetail(request.getDetail());
            movie.setReleaseYear(request.getReleaseYear());
            movie.setCountryCode(request.getCountryCode());
            movie.setImdb(request.getImdb());
            Director director = movieServiceRequest.getDirectorService().findById(request.getDirectorId());
            movie.setDirector(director);
            List<Actor> actors = new ArrayList<>();
            request.getActorIdList().forEach(id -> {
                Actor actor = movieServiceRequest.getActorService().findById(id);
                actors.add(actor);
            });
            List<Category> categories = new ArrayList<>();
            request.getCategoryIdList().forEach(id -> {
                Category category = movieServiceRequest.getCategoryService().getMovieFromCategoryId(id);
                categories.add(category);
            });
            movie.setActors(actors);
            movie.setCategories(categories);
            response = movieServiceRequest.getMovieRepository().save(movie);
        } catch (Exception ex) {
            throw new DataException("Unexpected error occurred while saving");
        }
        return response;
    }

}
