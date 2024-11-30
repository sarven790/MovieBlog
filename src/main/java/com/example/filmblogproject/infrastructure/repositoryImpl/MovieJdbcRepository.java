package com.example.filmblogproject.infrastructure.repositoryImpl;

import com.example.filmblogproject.domain.entity.Actor;
import com.example.filmblogproject.domain.entity.Category;
import com.example.filmblogproject.domain.entity.Director;
import com.example.filmblogproject.domain.entity.Movie;
import com.example.filmblogproject.interfaceAdapters.dto.request.repository.RepositoryRequestByLong;
import com.example.filmblogproject.interfaceAdapters.dto.request.repository.RepositoryRequestByString;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MovieJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Movie> getMovieByCountryCode(RepositoryRequestByString requestByString) {
        String sql = "SELECT M.* FROM MOVIE M WHERE M.COUNTRY_CODE=?";
        List<Map<String,Object>> responseList = jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByString.string());
        return convertToList(responseList);
    }

    public List<Movie> getMovieByDirectorId(RepositoryRequestByLong requestByLong) {
        String sql = "SELECT M.* FROM MOVIE M WHERE M.DIRECTOR_ID=?";
        List<Map<String,Object>> responseList = jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByLong.id());
        return convertToList(responseList);
    }

    public List<Movie> getMovieByReleaseYear(RepositoryRequestByString requestByString) {
        String sql = "SELECT M.* FROM MOVIE M WHERE M.RELEASE_YEAR=?";
        List<Map<String,Object>> responseList = jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByString.string());
        return convertToList(responseList);
    }

    public List<Movie> getMovieByActorId(RepositoryRequestByLong requestByLong) {
        String sql = "SELECT M.* FROM MOVIE M INNER JOIN MOVIE_ACTOR MA ON MA.MOVIE_ID=M.ID WHERE  MA.ACTOR_ID=?";
        List<Map<String,Object>> responseList = jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByLong.id());
        return convertToList(responseList);
    }

    private List<Map<String,Object>> getDirectorById(RepositoryRequestByLong requestByLong) {
        String sql = "SELECT * FROM DIRECTOR D WHERE D.ID=?";
        return jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByLong.id());
    }

    private List<Map<String,Object>> getActorListByMovieId(RepositoryRequestByLong requestByLong) {
        String sql = "SELECT A.* FROM ACTOR A INNER JOIN MOVIE_ACTOR MA ON MA.ACTOR_ID=A.ID INNER JOIN MOVIE M ON MA.MOVIE_ID=M.ID WHERE M.ID=?";
        return jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByLong.id());
    }

    private List<Map<String,Object>> getCategoryListByMovieId(RepositoryRequestByLong requestByLong) {
        String sql = "SELECT C.* FROM CATEGORY C INNER JOIN MOVIE_CATEGORY MC ON MC.CATEGORY_ID=C.ID INNER JOIN MOVIE M ON MC.MOVIE_ID=M.ID WHERE M.ID=?";
        return jdbcTemplate.getJdbcTemplate().queryForList(sql,requestByLong.id());
    }

    private List<Movie> convertToList(List<Map<String,Object>> mapList) {

        List<Movie> movieList = new ArrayList<>();

        mapList.forEach(dataMap-> {
            Movie movie = new Movie();
            movie.setTitle(dataMap.get("TITLE").toString());
            movie.setCreatedDate((OffsetDateTime) dataMap.get("CREATED_DATE"));
            movie.setModifiedDate((OffsetDateTime) dataMap.get("MODIFIED_DATE"));
            movie.setDetail(dataMap.get("DETAIL").toString());
            movie.setImdb(dataMap.get("IMDB").toString());
            movie.setReleaseYear(dataMap.get("RELEASE_YEAR").toString());
            movie.setCountryCode(dataMap.get("COUNTRY_CODE").toString());
            getDirectorById(new RepositoryRequestByLong((Long) dataMap.get("DIRECTOR_ID"))).forEach(directorMap-> {
                Director director = new Director();
                director.setName(directorMap.get("NAME").toString());
                movie.setDirector(director);
            });
            List<Actor> actors = new ArrayList<>();
            getActorListByMovieId(new RepositoryRequestByLong((Long) dataMap.get("ID"))).forEach(actorMap -> {
                Actor actor = new Actor();
                actor.setName(actorMap.get("NAME").toString());
                actors.add(actor);
            });
            movie.setActors(actors);
            List<Category> categories = new ArrayList<>();
            getCategoryListByMovieId(new RepositoryRequestByLong((Long) dataMap.get("ID"))).forEach(categoryMap-> {
                Category category = new Category();
                category.setName(categoryMap.get("NAME").toString());
                categories.add(category);
            });
            movie.setCategories(categories);
            movieList.add(movie);
        });
        return movieList;
    }

}
