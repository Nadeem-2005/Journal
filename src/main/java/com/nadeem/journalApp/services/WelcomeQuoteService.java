package com.nadeem.journalApp.services;

import com.nadeem.journalApp.DTO.QuotesResponse;
import com.nadeem.journalApp.config.AppCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WelcomeQuoteService {

//    private String url = "https://motivational-spark-api.vercel.app/api/quotes/random";

    private final WebClient webClient;
    private final AppCache appCache;


    public  Mono<QuotesResponse> welcomeQuoteGeneratorService(){
        Mono<QuotesResponse> response = webClient.get()
                .uri(appCache.appCache.get("quote_url"))
                .retrieve().bodyToMono(QuotesResponse.class);

        return response;
    }

}
