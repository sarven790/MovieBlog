package com.example.filmblogproject.interfaceAdapters.controller;

import com.example.filmblogproject.application.service.ActorService;
import com.example.filmblogproject.domain.entity.Actor;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.ActorRequest;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @QueryMapping
    public List<Actor> getAllActor() throws DataException {
        return actorService.getAllActor();
    }

    @MutationMapping
    public Actor createActor(@Argument ActorRequest actorRequest) throws DataException {
        return actorService.createActor(actorRequest);
    }

}
