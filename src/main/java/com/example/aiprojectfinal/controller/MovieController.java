package com.example.aiprojectfinal.controller;

import com.example.aiprojectfinal.dto.MovieDto;
import com.example.aiprojectfinal.service.AiClientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController
{
    private final AiClientService service;

    public MovieController(AiClientService service)
    {
        this.service = service;
    }

    //Returnere en liste af populære film
    @GetMapping("/popular-list")
    public Mono<List<MovieDto>> getPopularMoviesList() {
        return service.getPopularMoviesList();
    }
}
