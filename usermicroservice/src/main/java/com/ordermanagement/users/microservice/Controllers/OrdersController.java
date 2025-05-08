package com.ordermanagement.users.microservice.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.users.microservice.model.Orders;
import com.ordermanagement.users.microservice.service.Orderservice;
import com.ordermanagement.users.microservice.serviceImpl.OrderServiceImpl;
import com.ordermanagement.users.response.CustomResponse;

@RestController
@RequestMapping("/order-management")
public class OrdersController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private Orderservice orderService;

    @PostMapping("/create")
    public CustomResponse createOrder(@RequestBody Orders order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/update")
    public CustomResponse updateOrder(@PathVariable String orderId, @RequestBody Orders order) {
        return orderService.updateOrder(order);
    }

    @PutMapping("/cancel/{orderId}")
    public CustomResponse cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }

    @GetMapping("/{orderId}")
    public CustomResponse getOrder(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/getAllOerders")
    public CustomResponse getAllOrders() {
        return orderService.getAllOrders();
    }
}
