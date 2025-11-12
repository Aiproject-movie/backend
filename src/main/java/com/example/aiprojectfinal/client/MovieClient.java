package com.example.aiprojectfinal.client;

import com.example.aiprojectfinal.dto.MovieDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MovieClient {
    private final WebClient tmdbWebClient;

    public MovieClient(@Qualifier("tmdbWebClient") WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    //Kalder på vores record dto klasse
    public record TmdbResponse(int page, List<TmdbMovie> results, int total_pages, int total_results) {}
    public record TmdbMovie(long id, String title, String overview, double vote_average,
                            String poster_path, String release_date) {}


    //Kald TMDB: /3/movie/popular
    public Mono<TmdbResponse> fetchPopular(int page, String language, String region) {
        return tmdbWebClient.get()
                .uri(uri -> uri.path("/3/movie/popular")
                        .queryParam("page", page)
                        .queryParam("language", language)   // fx "en-US" eller "da-DK"
                        .queryParam("region", region)       // fx "DK" (valgfrit)
                        // hvis du ikke har lagt api_key i WebClientConfig, så tilføj:
                        // .queryParam("api_key", "<din_v3_api_key>")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                        .map(msg -> new IllegalStateException("TMDB error: " + msg)))
                .bodyToMono(TmdbResponse.class);
    }

    // Stream filmene som Flux
    public Flux<TmdbMovie> streamPopular(int page, String language, String region) {
        return fetchPopular(page, language, region)
                .flatMapMany(resp -> Flux.fromIterable(resp.results()));
    }

    private MovieDto toDto(TmdbMovie m) {
        return new MovieDto(
                m.id(),
                m.title(),
                m.overview(),
                m.vote_average(),
                buildPosterUrl(m.poster_path())
        );
    }

    private String buildPosterUrl(String path) {
        return (path == null || path.isBlank()) ? null : "https://image.tmdb.org/t/p/w500" + path;
    }

}
