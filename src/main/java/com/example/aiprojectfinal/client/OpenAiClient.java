package com.example.aiprojectfinal.client;

import org.springframework.beans.factory.annotation.Qualifier;
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

    public Mono<SimpleResponse> getResponses(String prompt) {

    }

}
