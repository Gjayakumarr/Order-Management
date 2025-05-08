package com.ordermanagement.users.microservice.service;

import com.ordermanagement.users.microservice.model.Orders;
import com.ordermanagement.users.response.CustomResponse;

public interface Orderservice {

	CustomResponse createOrder(Orders order);

	CustomResponse updateOrder(Orders updatedOrder);

	CustomResponse cancelOrder(String orderId);

	CustomResponse getOrderById(String orderId);

	CustomResponse getAllOrders();
}
