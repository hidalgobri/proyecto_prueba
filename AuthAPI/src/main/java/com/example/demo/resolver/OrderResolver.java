package com.example.demo.resolver;

import com.example.demo.dto.OrderInput;
import com.example.demo.dto.ProductInput;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderResolver{

    private final OrderService orderService;

    public OrderResolver(OrderService orderRepository, OrderService orderService){
        this.orderService = orderService;
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<Order> orders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Order orderById(@Argument Long id) {
        return orderService.getOrderById(id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Order createOrder(@Argument OrderInput input) {
        return orderService.createOrder(input);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Boolean deleteOrder(@Argument Long id) {
        return orderService.deleteOrder(id);
    }
    
}