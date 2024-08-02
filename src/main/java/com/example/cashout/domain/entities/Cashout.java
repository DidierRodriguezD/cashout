package com.example.cashout.domain.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cashouts")
public class Cashout {
    @Id
    private String id;

    @TextIndexed
    @NotBlank(message = "Usuario es requerido")
    @NotEmpty(message = "Usuario no puede estar vacío")
    @NotNull(message = "Usuario no puede ser nulo")
    private String userId;

    @Min(value = 1, message = "Monto debe ser mayor a 0")
    @NotNull(message = "Monto no puede ser nulo")
    private Double amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank(message = "Usuario es requerido") @NotEmpty(message = "Usuario no puede estar vacío") @NotNull(message = "Usuario no puede ser nulo") String getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank(message = "Usuario es requerido") @NotEmpty(message = "Usuario no puede estar vacío") @NotNull(message = "Usuario no puede ser nulo") String userId) {
        this.userId = userId;
    }

    public @Min(value = 1, message = "Monto debe ser mayor a 0") @NotNull(message = "Monto no puede ser nulo") Double getAmount() {
        return amount;
    }

    public void setAmount(@Min(value = 1, message = "Monto debe ser mayor a 0") @NotNull(message = "Monto no puede ser nulo") Double amount) {
        this.amount = amount;
    }
}
