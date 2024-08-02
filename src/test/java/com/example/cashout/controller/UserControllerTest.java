package com.example.cashout.controller;

import com.example.cashout.domain.entities.Amount;
import com.example.cashout.domain.entities.User;
import com.example.cashout.domain.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setBalance(100.0);
        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        // Act & Assert
        webTestClient.get().uri("/users/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.name").isEqualTo(user.getName())
                .jsonPath("$.balance").isEqualTo(user.getBalance());
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        String userId = "123";
        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.get().uri("/users/{id}", userId)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("User not found");
    }

    @Test
    void createUser_ShouldReturnCreatedUser_WhenUserIsValid() {
        // Arrange
        User user = new User();
        user.setId("123");
        user.setName("John Doe");
        user.setBalance(100.0);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        // Act & Assert
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.name").isEqualTo(user.getName())
                .jsonPath("$.balance").isEqualTo(user.getBalance());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);
        user.setBalance(100.0);
        Amount amount = new Amount();
        amount.setAmount(50.0);
        when(userRepository.findById(userId)).thenReturn(Mono.just(user));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        // Act & Assert
        webTestClient.put().uri("/users/{userId}/balance", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(amount)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.name").isEqualTo(user.getName())
                .jsonPath("$.balance").isEqualTo(user.getBalance());
    }
}