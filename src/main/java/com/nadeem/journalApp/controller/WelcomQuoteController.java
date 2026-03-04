package com.nadeem.journalApp.controller;

import com.nadeem.journalApp.DTO.QuotesResponse;
import com.nadeem.journalApp.services.WelcomeQuoteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WelcomQuoteController {

    private final WelcomeQuoteService welcomeQuoteService;
    @GetMapping("/")
    public ResponseEntity<?> welcomeQuoteGenerator(){
        try {
            //.block() is used here because our app uses MVC and .block() converts Mono<QuoteResponse> to QuoteResponse
            QuotesResponse quotesResponse = welcomeQuoteService.welcomeQuoteGeneratorService().block();

            return new ResponseEntity<>(quotesResponse,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
