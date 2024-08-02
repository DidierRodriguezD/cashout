package com.example.cashout.domain.repositories;

import com.example.cashout.domain.entities.Cashout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CashoutRepository extends ReactiveMongoRepository<Cashout, String> {

    Flux<Cashout> findByUserId(String userId);

}
