package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderEntity;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    OrderServiceImpl (OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order retrieveOrder(String orderId) {
        //Conversion from string to integer to prevent null pointer exception
        Integer id = Integer.valueOf(orderId);
        //The new version of spring data jpa returns Optional instead of the actual entity
        // fetch an individual customer by ID
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToOrderResponse(orderEntity);

    }

    private Order mapToOrderResponse(OrderEntity orderEntity) {
        //You want the entity to be different than the response returned to the user. To separate the data layer from the presentation layer.
        Order orderResponse = new Order();
        orderResponse.setId(orderEntity.getId());
        orderResponse.setProduct(orderEntity.getProduct());
        orderResponse.setDescription(orderEntity.getDescription());
        orderResponse.setQuantity(orderEntity.getQuantity());
        return orderResponse;
    }

    @Override
    public Order saveOrder(Order orderRequest) {
        //you can do validations of the orderRequest here
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProduct(orderRequest.getProduct());
        orderEntity.setDescription(orderRequest.getDescription());
        orderEntity.setQuantity(orderRequest.getQuantity());

        //you can check for duplicates in DB here before saving.
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return mapToOrderResponse(savedOrder);
    }

    @Override
    public List<Order> retrieveOrderByProduct(String product) {
        List<OrderEntity> ordersRetrieved = orderRepository.findByProduct(product);
        List<Order> ordersFound = new ArrayList<>();
        if(ordersRetrieved == null || ordersRetrieved.isEmpty()){
            throw new RuntimeException("Orders Not Found");
        }else{
            for(OrderEntity order : ordersRetrieved){
                ordersFound.add(mapToOrderResponse(order));
            };

        }
        return ordersFound;
    }
}
