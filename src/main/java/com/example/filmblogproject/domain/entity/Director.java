package com.example.filmblogproject.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Director extends BaseEntity{

    @OneToMany(mappedBy = "director")
    private Set<Movie> movies;

    private String name;

}
