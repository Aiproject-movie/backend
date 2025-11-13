package com.example.aiprojectfinal.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OpenAiClient {
    private final WebClient openAiWebClient;

    public OpenAiClient(@Qualifier("openAiWebClient") WebClient openAiWebClient){
        this.openAiWebClient = openAiWebClient;
    }

    public record SimpleResponse(String id, List<Output> output) {}
    public record Output(String id, List<Content> content) {}
    public record Content(String text) {}

    public record SimpleRequest(String model, String input) {}

    public Mono<SimpleResponse> getResponse(String prompt) {
        SimpleRequest body = new SimpleRequest("openai", prompt);
        return openAiWebClient.post()
                .uri("/responses")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(s -> s.value() == 400, r -> r.bodyToMono(String.class)
                        .map(msg -> new IllegalArgumentException("OpenAI 400: " + msg)))
                .onStatus(s -> s.value() == 401, r -> r.bodyToMono(String.class)
                        .map(msg -> new IllegalArgumentException("OpenAI 401 Unauthorized: " + msg)))
                .onStatus(HttpStatusCode::isError, r -> r.bodyToMono(String.class)
                        .map(msg -> new IllegalArgumentException("OpenAI Error: " + msg)))
                .bodyToMono(SimpleResponse.class);
    }
}


