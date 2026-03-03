package com.example.demo.dto;

import java.util.List;

public class OrderInput{
    private List<OrderItemsInput> orderItemsInput;

    public List<OrderItemsInput> getOrderItemsInput() {
        return orderItemsInput;
    }

    public void setOrderItemsInput(List<OrderItemsInput> orderItemsInput) {
        this.orderItemsInput = orderItemsInput;
    }
}