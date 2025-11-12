package com.example.aiprojectfinal.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MovieClient {
    private final WebClient tmdbWebClient;

    public MovieClient(@Qualifier("tmdbWebClient") WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    public record ApiClient (String title, String overview, String posterUrl) {}

   /* public Mono<JsonplaceholderClient.ApiClient> searchMovie(String title) {
        return apiClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", title)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("results").path(0)) // tager første resultat
                .filter(node -> !node.isMissingNode())
                .map(node -> new JsonplaceholderClient.ApiClient()apiClient(
                        node.path("title").asText("Unknown"),
                        node.path("overview").asText("No overview available"),
                        "https://image.tmdb.org/t/p/w500" + node.path("poster_path").asText("")
                ));
    }*/
}
