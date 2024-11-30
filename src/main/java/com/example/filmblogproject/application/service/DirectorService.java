package com.example.filmblogproject.application.service;

import com.example.filmblogproject.domain.entity.Director;
import com.example.filmblogproject.domain.repository.DirectorRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.DirectorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public Director createDirector(DirectorRequest request) {
        Director director = new Director();
        director.setName(request.getName());
        return directorRepository.save(director);
    }

    public Director findById(Long id) {
        Optional<Director> optDirector = directorRepository.findById(id);
        Director director = new Director();
        if (optDirector.isPresent()) {
            director = optDirector.get();
        }
        return director;
    }

    public List<Director> getAllDirector() {
        return directorRepository.findAll();
    }

}
