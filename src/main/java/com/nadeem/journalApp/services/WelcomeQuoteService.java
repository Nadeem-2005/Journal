package com.nadeem.journalApp.services;

import com.nadeem.journalApp.DTO.QuotesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WelcomeQuoteService {

    private String url = "https://motivational-spark-api.vercel.app/api/quotes/random";
    private final WebClient webClient;


    public  Mono<QuotesResponse> welcomeQuoteGeneratorService(){
        Mono<QuotesResponse> response = webClient.get()
                .uri(url)
                .retrieve().bodyToMono(QuotesResponse.class);

        return response;
    }

}
