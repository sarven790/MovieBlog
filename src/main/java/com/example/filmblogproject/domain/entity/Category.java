package com.example.filmblogproject.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category extends BaseEntity{

    @ManyToMany(mappedBy = "categories")
    private List<Movie> movies;
    private String name;

}
