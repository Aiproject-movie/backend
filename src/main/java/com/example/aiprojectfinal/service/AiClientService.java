package com.example.aiprojectfinal.service;

import com.example.aiprojectfinal.dto.MovieDto;
import com.example.aiprojectfinal.dto.TmdbResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AiClientService {

    private final WebClient tmdbWebClient;

    public AiClientService(@Qualifier("tmdbWebClient") WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    //Henter populære film fra TMDb som et Flux<MovieDto>
    public Flux<MovieDto> getPopularMovies() {
        return tmdbWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/popular")
                        .queryParam("api_key", "a7ecf80fb48396b1ba8342c9cef45439")
                        .build())
                .retrieve()
                .bodyToMono(TmdbResponse.class)
                .flatMapMany(resp -> Flux.fromIterable(resp.results()))
                .map(movie -> new MovieDto(
                        movie.id(),
                        movie.title(),
                        movie.overview(),
                        movie.vote_average(),
                        "https://image.tmdb.org/t/p/w500" + movie.poster_path()
                ));
    }

    //Returnerer alle populære film som en samlet liste (Mono<List<MovieDto>>)
    public Mono<List<MovieDto>> getPopularMoviesList() {
        return getPopularMovies().collectList();
    }
}
