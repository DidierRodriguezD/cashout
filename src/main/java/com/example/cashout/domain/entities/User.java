package com.example.cashout.domain.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Nombre es requerido")
    @NotEmpty(message = "Nombre no puede estar vacío")
    @NotNull(message = "Nombre no puede ser nulo")
    private String name;

    @Min(value = 1, message = "Balance debe ser mayor a 0")
    @NotNull(message = "Balance no puede ser nulo")
    private Double balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank(message = "Nombre es requerido") @NotEmpty(message = "Nombre no puede estar vacío") @NotNull(message = "Nombre no puede ser nulo") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Nombre es requerido") @NotEmpty(message = "Nombre no puede estar vacío") @NotNull(message = "Nombre no puede ser nulo") String name) {
        this.name = name;
    }

    public @Min(value = 1, message = "Balance debe ser mayor a 0") @NotNull(message = "Balance no puede ser nulo") Double getBalance() {
        return balance;
    }

    public void setBalance(@Min(value = 1, message = "Balance debe ser mayor a 0") @NotNull(message = "Balance no puede ser nulo") Double balance) {
        this.balance = balance;
    }
}