package com.example.cashout.services.interfaces;

import com.example.cashout.domain.entities.Cashout;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IPaymentsRestClient {

    @PostExchange("/payments")
    Mono<String> getPaymentsStatus(@RequestBody Cashout cashout);

}
