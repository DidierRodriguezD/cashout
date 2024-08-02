package com.example.cashout.domain.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Amount {
    @Min(value = 1, message = "Monto debe ser mayor a 0")
    @NotNull(message = "Monto no puede ser nulo")
    private Double amount;

    public @Min(value = 1, message = "Monto debe ser mayor a 0") @NotNull(message = "Monto no puede ser nulo") Double getAmount() {
        return amount;
    }

    public void setAmount(@Min(value = 1, message = "Monto debe ser mayor a 0") @NotNull(message = "Monto no puede ser nulo") Double amount) {
        this.amount = amount;
    }
}
