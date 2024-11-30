package com.example.filmblogproject.interfaceAdapters.controller;

import com.example.filmblogproject.application.service.DirectorService;
import com.example.filmblogproject.domain.entity.Director;
import com.example.filmblogproject.interfaceAdapters.dto.request.service.DirectorRequest;
import com.example.filmblogproject.interfaceAdapters.handler.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @QueryMapping
    public List<Director> getAllDirector() throws DataException {
        return directorService.getAllDirector();
    }

    @MutationMapping
    public Director createDirector(@Argument DirectorRequest directorRequest) throws DataException {
        return directorService.createDirector(directorRequest);
    }

}
