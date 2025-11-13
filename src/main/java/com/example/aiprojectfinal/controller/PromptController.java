package com.example.aiprojectfinal.controller;

import com.example.aiprojectfinal.client.MovieClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/prompt")
public class PromptController {

    private final MovieClient movieClient;

    public PromptController(MovieClient movieClient) {
        this.movieClient = movieClient;
    }

    // request body fra frontend: { "prompt": "..." }
    public record QueryRequest(String prompt) {}

    // det vi sender tilbage til frontend: { "response": "..." }
    public record ResponseDto(String response) {}

    @PostMapping
    public Mono<ResponseEntity<ResponseDto>> ask(@RequestBody QueryRequest queryRequest) {
        String prompt = queryRequest.prompt();  // det brugeren skrev i chatten

        int page = 1;
        String language = "da-DK";  // eller "da-DK" hvis du vil
        String region = "DK";       // valgfrit – kan også være "" eller "US"

        return movieClient.streamPopular(page, language, region)
                .take(5)                         // kun de 5 første film
                .collectList()                   // saml dem til en List<TmdbMovie>
                .map(movies -> {
                    StringBuilder sb = new StringBuilder();

                    if (prompt != null && !prompt.isBlank()) {
                        sb.append("Du skrev: \"")
                                .append(prompt)
                                .append("\"\n\n");
                    }

                    sb.append("Her er nogle populære film lige nu:\n\n");

                    for (MovieClient.TmdbMovie m : movies) {
                        sb.append("- ")
                                .append(m.title());

                        if (m.release_date() != null && !m.release_date().isBlank()) {
                            sb.append(" (").append(m.release_date()).append(")");
                        }

                        sb.append("  ⭐ ")
                                .append(String.format("%.1f", m.vote_average()))
                                .append("/10");

                        sb.append("\n");
                    }

                    return ResponseEntity.ok(new ResponseDto(sb.toString()));
                })
                .onErrorResume(ex ->
                        Mono.just(ResponseEntity
                                .status(500)
                                .body(new ResponseDto("Der skete en fejl mod TMDB: " + ex.getMessage())))
                );
    }
}
