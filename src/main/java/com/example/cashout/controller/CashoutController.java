package com.example.cashout.controller;

import com.example.cashout.domain.entities.Cashout;
import com.example.cashout.exception.CashoutNoFoundException;
import com.example.cashout.exception.PaymentsException;
import com.example.cashout.services.interfaces.ICashoutService;
import com.example.cashout.services.interfaces.IPaymentsRestClient;
import com.example.cashout.services.interfaces.ITransactionHistoryRestClient;
import com.example.cashout.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/cashouts")
public class CashoutController {
    private final ICashoutService iCashoutService;
    private final IUserService iUserService;
    private final IPaymentsRestClient iPaymentsRestClient;
    private final ITransactionHistoryRestClient iTransactionHistoryRestClient;

    public CashoutController(ICashoutService iCashoutService, IUserService iUserService, IPaymentsRestClient iPaymentsRestClient, ITransactionHistoryRestClient iTransactionHistoryRestClient) {
        this.iCashoutService = iCashoutService;
        this.iUserService = iUserService;
        this.iPaymentsRestClient = iPaymentsRestClient;
        this.iTransactionHistoryRestClient = iTransactionHistoryRestClient;
    }

    @PostMapping
    public Mono<Cashout> createCashout(@Valid @RequestBody Cashout cashout){
        return iUserService.getUserById(cashout.getUserId())
                .flatMap(user -> {
                    if (user.getBalance() >= cashout.getAmount()) {
                        return Mono.just(user);
                    } else {
                        return Mono.error(new CashoutNoFoundException("Balance insuficiente"));
                    }
                })
                .flatMap(user -> iPaymentsRestClient.getPaymentsStatus(cashout)
                    .flatMap(status -> {
                        if (status.equals("approved")) {
                            return Mono.just(user);
                        } else {
                            return Mono.error(new PaymentsException("Payment failed"));
                        }
                    })
                ).flatMap(user -> {
                    user.setBalance(user.getBalance() - cashout.getAmount());
                    return iUserService.updateUserBalance(user);
                })
                .flatMap(user -> iCashoutService.create(cashout));
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getCashoutById(@PathVariable String userId){
        return iTransactionHistoryRestClient.getCashoutById(userId);
    }


}
