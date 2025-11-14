package com.example.aiprojectfinal.controller;

import com.example.aiprojectfinal.dto.MovieDto;
import com.example.aiprojectfinal.service.AiClientService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class AiController {

    private final AiClientService aiClientService;

    public AiController(AiClientService aiClientService) {
        this.aiClientService = aiClientService;
    }

    // Simpelt health-check
    @GetMapping("/test")
    public String test() {
        return "Backend is running";
    }

    // Chat-endpoint som din frontend kalder: POST /api/movies/popular-list
    @PostMapping("/popular-list")
    public Mono<Map<String, String>> chatWithPopularList(@RequestBody PromptRequest request) {
        String prompt = request.prompt();
        String lower = prompt.toLowerCase();

        // Hvis brugeren beder om film/anbefaling, henter vi populære film
        if (lower.contains("film") || lower.contains("movie") || lower.contains("anbefal")) {
            return aiClientService.getPopularMoviesList()
                    .map(this::buildMovieReply);
        }

        // Fallback: bare svar tilbage med det brugeren skrev
        String answer = "Du skrev: " + prompt ;
        return Mono.just(Map.of("response", answer));
    }

    // Bygger et tekstsvar ud fra en liste af film
    private Map<String, String> buildMovieReply(List<MovieDto> movies) {
        if (movies == null || movies.isEmpty()) {
            return Map.of("response", "Jeg kunne ikke finde nogle populære film lige nu 😢");
        }

        int limit = Math.min(3, movies.size());

        StringBuilder sb = new StringBuilder("Her er nogle populære film lige nu:\n\n");

        for (int i = 0; i < limit; i++) {
            MovieDto m = movies.get(i);

            sb.append(i + 1).append(". ").append(m.title());

            // Hvis MovieDto har rating
            if (m.rating() > 0) {
                sb.append(" (").append(m.rating()).append("/10)");
            }

            // Hvis MovieDto har beskrivelse
            if (m.description() != null && !m.description().isBlank()) {
                sb.append("\n   ").append(m.description());
            }

            sb.append("\n\n");
        }

        return Map.of("response", sb.toString().trim());
    }

    // Bruges til at læse JSON-body { "prompt": "..." }
    public record PromptRequest(String prompt) { }
}
