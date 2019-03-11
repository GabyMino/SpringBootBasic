package com.example.demo.service;

import com.example.demo.domain.Order;

import java.util.List;

public interface OrderService {

    Order retrieveOrder(String orderId);

    Order saveOrder(Order orderRequest);

    List<Order> retrieveOrderByProduct(String product);
}
