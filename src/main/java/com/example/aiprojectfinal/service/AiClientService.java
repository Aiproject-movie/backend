package com.example.aiprojectfinal.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class AiClientService {

    private final WebClient openAiWebClient;
    private final WebClient tmdbWebClient;

    public AiClientService(
            @Qualifier("openAiWebClient") WebClient openAiWebClient,
            @Qualifier("tmdbWebClient") WebClient tmdbWebClient) {

            this.openAiWebClient = openAiWebClient;
            this.tmdbWebClient = tmdbWebClient;
    }
}