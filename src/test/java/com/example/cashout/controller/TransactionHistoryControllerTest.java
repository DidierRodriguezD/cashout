package com.example.cashout.controller;

import com.example.cashout.domain.entities.Cashout;
import com.example.cashout.services.interfaces.ICashoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TransactionHistoryControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ICashoutService iCashoutService;

    @Test
    void getCashoutById_ShouldReturnCashouts_WhenCashoutsExist() {
        // Arrange
        String userId = "user123";
        Cashout cashout1 = new Cashout();
        cashout1.setId("1");
        Cashout cashout2 = new Cashout();
        cashout2.setId("2");
        when(iCashoutService.getCashoutById(userId)).thenReturn(Flux.just(cashout1, cashout2));

        // Act & Assert
        webTestClient.get().uri("/transaction-history/user/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cashout.class);
    }

    @Test
    void getCashoutById_ShouldReturnEmpty_WhenNoCashoutsExist() {
        // Arrange
        String userId = "user123";
        when(iCashoutService.getCashoutById(userId)).thenReturn(Flux.empty());

        // Act & Assert
        webTestClient.get().uri("/transaction-history/user/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cashout.class)
                .hasSize(0);
    }
}