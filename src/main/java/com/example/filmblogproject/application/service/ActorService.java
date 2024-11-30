package com.example.filmblogproject.application.service;

import com.example.filmblogproject.domain.entity.Actor;
import com.example.filmblogproject.domain.repository.ActorRepository;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.ActorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public List<Actor> getAllActor() {
        return actorRepository.findAll();
    }

    public Actor createActor(ActorRequest request) {

        Actor actor = new Actor();
        actor.setName(request.getName());

        return actorRepository.save(actor);
    }

    public Actor findById(Long id) {
        Optional<Actor> optActor = actorRepository.findById(id);
        Actor actor = new Actor();
        if (optActor.isPresent()) {
            actor = optActor.get();
        }
        return actor;
    }

}
