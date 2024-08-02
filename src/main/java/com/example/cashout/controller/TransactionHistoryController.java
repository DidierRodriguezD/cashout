package com.example.cashout.controller;

import com.example.cashout.domain.entities.Cashout;
import com.example.cashout.services.interfaces.ICashoutService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Validated
@RestController
@RequestMapping("/transaction-history")
public class TransactionHistoryController {

    private final ICashoutService iCashoutService;

    public TransactionHistoryController(ICashoutService iCashoutService) {
        this.iCashoutService = iCashoutService;
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getCashoutById(@PathVariable String userId){
        return iCashoutService.getCashoutById(userId);
    }
}
