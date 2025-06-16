package com.assignment.orderstatemachine.model;


public class Order {
    private Long id;
    private String description;
    private OrderState state;

    public Order(Long id, String description) {
        this.id = id;
        this.description = description;
        this.state = OrderState.NEW; // Default initial state
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
