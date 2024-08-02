package com.example.cashout.services;

import com.example.cashout.domain.entities.User;
import com.example.cashout.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userService.getUserById(userId);

        // Assert
        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void getUserById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Arrange
        String userId = "123";
        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        // Act
        Mono<User> result = userService.getUserById(userId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void updateUserBalance_ShouldReturnUpdatedUser_WhenUserIsSaved() {
        // Arrange
        User user = new User();
        user.setId("123");
        user.setBalance(100.0);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userService.updateUserBalance(user);

        // Assert
        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

}