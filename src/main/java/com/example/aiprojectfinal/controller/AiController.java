package com.example.aiprojectfinal.controller;

import com.example.aiprojectfinal.dto.MovieDto;
import com.example.aiprojectfinal.dto.TmdbResponse;
import com.example.aiprojectfinal.service.AiClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AiController {

    private final AiClientService aiClientService;

    public AiController(AiClientService aiClientService) {
        this.aiClientService = aiClientService;
    }

    // 1) Health-check / test
    @GetMapping("/test")
    public String test() {
        return "✅ Backend is running";
    }

    // 2) Hent kun film-info fra TMDb
//    @GetMapping("/movie")
//    public Mono<MovieDto> getMovie(@RequestParam String title) {
//        return aiClientService.fetchMovie(title); // implementeres i AiClientService //idk yet.
//    }
//
//    // 3) Kombiner TMDb + OpenAI (AI-forklaring)
//    @GetMapping("/assistant/analyze")
//    public Mono<TmdbResponse> analyze(@RequestParam String title) {
//        return aiClientService.analyzeMovie(title); // implementeres i AiClientService
    }
