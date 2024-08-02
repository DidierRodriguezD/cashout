package com.example.cashout.services;

import com.example.cashout.domain.entities.User;
import com.example.cashout.domain.repositories.UserRepository;
import com.example.cashout.services.interfaces.IUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Mono<User> updateUserBalance(User user) {
        return userRepository.save(user);
    }
}
