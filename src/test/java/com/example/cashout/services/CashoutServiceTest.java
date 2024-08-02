package com.example.cashout.services;

import com.example.cashout.domain.entities.Cashout;
import com.example.cashout.domain.repositories.CashoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CashoutServiceTest {
    @Mock
    private CashoutRepository cashoutRepository;

    @InjectMocks
    private CashoutService cashoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnSavedCashout_WhenCashoutIsSaved() {
        // Arrange
        Cashout cashout = new Cashout();
        cashout.setId("123");
        when(cashoutRepository.save(any(Cashout.class))).thenReturn(Mono.just(cashout));

        // Act
        Mono<Cashout> result = cashoutService.create(cashout);

        // Assert
        StepVerifier.create(result)
                .expectNext(cashout)
                .verifyComplete();
    }

    @Test
    void getCashoutById_ShouldReturnCashouts_WhenCashoutsExist() {
        // Arrange
        String userId = "user123";
        Cashout cashout1 = new Cashout();
        cashout1.setId("1");
        Cashout cashout2 = new Cashout();
        cashout2.setId("2");
        when(cashoutRepository.findByUserId(userId)).thenReturn(Flux.just(cashout1, cashout2));

        // Act
        Flux<Cashout> result = cashoutService.getCashoutById(userId);

        // Assert
        StepVerifier.create(result)
                .expectNext(cashout1)
                .expectNext(cashout2)
                .verifyComplete();
    }

    @Test
    void getCashoutById_ShouldReturnEmpty_WhenNoCashoutsExist() {
        // Arrange
        String userId = "user123";
        when(cashoutRepository.findByUserId(userId)).thenReturn(Flux.empty());

        // Act
        Flux<Cashout> result = cashoutService.getCashoutById(userId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();
    }
}