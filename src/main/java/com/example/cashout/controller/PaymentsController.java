package com.example.cashout.controller;

import com.example.cashout.domain.entities.Cashout;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> getPaymentsStatus(@Valid @RequestBody Cashout cashout) {
        String[] strings = {"approved", "not approved"};
        Random random = new Random();
        int randomIndex = random.nextInt(strings.length);
        return Mono.just(strings[randomIndex]);
    }

}
