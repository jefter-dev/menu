package br.edu.ifpb.poo.menu.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}

