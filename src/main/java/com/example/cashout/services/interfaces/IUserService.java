package com.example.cashout.services.interfaces;

import com.example.cashout.domain.entities.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> getUserById(String userId);
    Mono<User> updateUserBalance(User user);
}
