
package com.example.cashout.controller;

import com.example.cashout.domain.entities.Amount;
import com.example.cashout.domain.entities.User;
import com.example.cashout.domain.repositories.UserRepository;
import com.example.cashout.exception.Exception400Exception;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception400Exception("User not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{userId}/balance")
    public Mono<User> updateUser(@PathVariable String userId, @Valid @RequestBody Amount amount) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + amount.getAmount());
                    return userRepository.save(user);
                });
    }
}