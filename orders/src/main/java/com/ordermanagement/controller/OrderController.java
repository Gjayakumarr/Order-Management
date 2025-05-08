package com.ordermanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ordermanagement.model.CustomResponse;
import com.ordermanagement.model.Orders;
import com.ordermanagement.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public CustomResponse createOrder(@RequestBody Orders order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/update/{orderId}")
    public CustomResponse updateOrder(@PathVariable String orderId, @RequestBody Orders order) {
        return orderService.updateOrder(orderId, order);
    }

    @PutMapping("/cancel/{orderId}")
    public CustomResponse cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }

    @GetMapping("/getById/{orderId}")
    public CustomResponse getOrder(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/getAllOerders")
    public CustomResponse getAllOrders() {
        return orderService.getAllOrders();
    }
}
