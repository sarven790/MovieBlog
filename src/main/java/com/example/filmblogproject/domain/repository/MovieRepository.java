package com.example.filmblogproject.domain.repository;

import com.example.filmblogproject.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MovieRepository extends JpaRepository<Movie,Long> {
    @Query(value = "select m from Movie m where m.director.id = :director_id")
    List<Movie> getMovieByDirectorId(@Param("director_id")Long directorId);
}
