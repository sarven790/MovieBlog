package com.example.filmblogproject.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Actor extends BaseEntity{
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;
    private String name;
}
