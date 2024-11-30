package com.example.filmblogproject.domain.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Country extends BaseEntity{
    private String countryCode;
    private String countryName;
}
