package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private OrderRepository orderRepository;

    OrderController(OrderService orderService, OrderRepository orderRepository){
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    //Below is a different way to code lines 12 to 15
    //@Autowired
    //private OrderService orderService;

    //To access http://localhost:8080/orders?product=table
    //@RequestBody translates class to json or xml. Mo service layer
    @GetMapping("/orders")
    public @ResponseBody List<OrderEntity> getOrderByProduct(@RequestParam String product) {
        return orderRepository.findByProduct(product);
    }

    //To access http://localhost:8080/orders/1234
    @RequestMapping("orders/{someID}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value="someID") String id) {
        Order order = orderService.retrieveOrder(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> submitOrder(@RequestBody Order order) {
        Order orderSaved = orderService.saveOrder(order);
        return new ResponseEntity<>(orderSaved, HttpStatus.CREATED);
    }
}
