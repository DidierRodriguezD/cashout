package com.example.cashout.services;

import com.example.cashout.domain.entities.Cashout;
import com.example.cashout.domain.repositories.CashoutRepository;
import com.example.cashout.services.interfaces.ICashoutService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CashoutService implements ICashoutService {

    private final CashoutRepository cashoutRepository;

    public CashoutService(CashoutRepository cashoutRepository) {
        this.cashoutRepository = cashoutRepository;
    }

    @Override
    public Mono<Cashout> create(Cashout cashout) {
        return cashoutRepository.save(cashout);
    }

    @Override
    public Flux<Cashout> getCashoutById(String userId) {
        return cashoutRepository.findByUserId(userId);
    }
}
