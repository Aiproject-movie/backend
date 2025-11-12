package com.example.aiprojectfinal.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiClient {
    private final WebClient openAiWebClient;

    public OpenAiClient(@Qualifier("openAiWebClient") WebClient openAiWebClient){
        this.openAiWebClient = openAiWebClient;
    }


}
