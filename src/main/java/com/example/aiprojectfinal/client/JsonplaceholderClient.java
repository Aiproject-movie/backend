package com.example.aiprojectfinal.client;

import com.example.aiprojectfinal.config.WebClientConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

//T tror ikke at Jsonplaceholder er relevant ger i denne - Osman bruger den heller ikke i hans Chatgpt demo
@Service
public class JsonplaceholderClient {

        private final WebClient client;

        public JsonplaceholderClient(@Qualifier("jsonPlaceholderClient") WebClient client) {
                this.client = client;
        }

        public record AiMovieResponse(String original_title, String original_language, String overview) {}


        public Flux<AiMovieResponse> getAiResponse(){
                return client.get()
                        .uri("/openAi")
                        .retrieve()
                        .bodyToFlux(AiMovieResponse.class);
        }



        public record ApiClient (String title, String overview, String posterUrl) {}
}

