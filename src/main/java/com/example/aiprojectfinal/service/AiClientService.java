package com.example.aiprojectfinal.service;

import com.example.aiprojectfinal.client.OpenAiClient;
import com.example.aiprojectfinal.dto.MovieDto;
import com.example.aiprojectfinal.dto.TmdbResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AiClientService{

    @Autowired
    private final WebClient tmdbWebClient;

    public AiClientService(
            @Qualifier("tmdbWebClient") WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    //Henter populære film som en strøm - Flux
    public Flux<MovieDto> getPopularMovies()
    {
        return tmdbWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/popular")
                        .queryParam("api_key", "a7ecf80fb48396b1ba8342c9cef45439") //dette er vores Api key, som står i Https
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


    //Hvis vi gerne vil have returneret hele listen og ikke kun Populære film
        public Mono<List<MovieDto>> getPopularMoviesList() {
            return getPopularMovies().collectList();
        }
}





