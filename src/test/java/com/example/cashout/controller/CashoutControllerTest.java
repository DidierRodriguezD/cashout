package com.example.cashout.controller;

import com.example.cashout.domain.entities.Cashout;
import com.example.cashout.domain.entities.User;
import com.example.cashout.services.interfaces.ICashoutService;
import com.example.cashout.services.interfaces.IPaymentsRestClient;
import com.example.cashout.services.interfaces.ITransactionHistoryRestClient;
import com.example.cashout.services.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CashoutControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ICashoutService iCashoutService;

    @MockBean
    private IUserService iUserService;

    @MockBean
    private IPaymentsRestClient iPaymentsRestClient;

    @MockBean
    private ITransactionHistoryRestClient iTransactionHistoryRestClient;

    @Test
    void createCashout_Success() {
        User user = new User();
        user.setId("testUserId");
        user.setBalance(200.00);
        user.setName("testUser");

        Cashout cashout = new Cashout();
        cashout.setUserId(user.getId());
        cashout.setAmount(100.00);

        when(iUserService.getUserById(any(String.class))).thenReturn(Mono.just(user));
        when(iUserService.updateUserBalance(any(User.class))).thenReturn(Mono.just(user));
        when(iPaymentsRestClient.getPaymentsStatus(any(Cashout.class))).thenReturn(Mono.just("approved"));
        when(iCashoutService.create(any(Cashout.class))).thenReturn(Mono.just(cashout));

        webTestClient.post().uri("/cashouts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cashout)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.amount").isEqualTo(100);
    }

    @Test
    void getCashoutById_Success() {
        String userId = "testUserId";
        when(iTransactionHistoryRestClient.getCashoutById(any(String.class))).thenReturn(Flux.just(new Cashout(), new Cashout()));

        webTestClient.get().uri("/cashouts/user/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cashout.class)
                .hasSize(2);
    }
}