package com.example.demo.service;

import com.example.demo.dto.OrderInput;
import com.example.demo.dto.OrderItemsInput;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository)
    {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(OrderInput order) {
        List<OrderItem> newOrderItems = new ArrayList<>();
        Order newOrder = new Order();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =this.userRepository.findByUsername(userName).orElse(new User());

        for(OrderItemsInput item : order.getOrderItemsInput()){
            OrderItem newOrderItem = new OrderItem();

            newOrderItem.setProduct( productRepository.findById(item.getProductId()).orElse(new Product()) );
            newOrderItem.setOrder(newOrder);
            newOrderItem.setQuantity(item.getQuantity());
            newOrderItem.setAddedDate(LocalDateTime.now());

            newOrderItems.add(newOrderItem);
        }

        newOrder.setItems(newOrderItems);
        newOrder.setUser(user);
        newOrder.setCreationDate(LocalDateTime.now());

        return orderRepository.save(newOrder);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public boolean deleteOrder(Long id)
    {
        Order order = getOrderById(id);
        orderRepository.delete(order);
        return true;
    }

}