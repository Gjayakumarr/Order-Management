package com.ordermanagement.service;

import com.ordermanagement.model.CustomResponse;
import com.ordermanagement.model.Orders;

public interface OrderService {

    CustomResponse createOrder(Orders order);

    CustomResponse updateOrder(String orderId, Orders updatedOrder);

    CustomResponse cancelOrder(String orderId);

    CustomResponse getOrderById(String orderId);

    CustomResponse getAllOrders();
}
