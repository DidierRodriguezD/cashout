package com.example.cashout.services.interfaces;

import com.example.cashout.domain.entities.Cashout;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;

public interface ITransactionHistoryRestClient {
    @GetExchange("/transaction-history/user/{userId}")
    Flux<Cashout> getCashoutById(@PathVariable("userId") String userId);
}
