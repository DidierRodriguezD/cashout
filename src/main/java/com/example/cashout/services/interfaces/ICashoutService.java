package com.example.cashout.services.interfaces;

import com.example.cashout.domain.entities.Cashout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICashoutService {
    Mono<Cashout> create(Cashout cashout);
    Flux<Cashout> getCashoutById(String userId);
}
