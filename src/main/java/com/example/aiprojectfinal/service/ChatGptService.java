package com.example.aiprojectfinal.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class ChatGptService {

    private final WebClient openAiClient;
    private final WebClient tmdbClient;

    public ChatGptService(
            @Qualifier("openAiClient") WebClient openAiClient,
            @Qualifier("tmdbClient") WebClient tmdbClient
    ) {
        this.openAiClient = openAiClient;
        this.tmdbClient = tmdbClient;
    }    }