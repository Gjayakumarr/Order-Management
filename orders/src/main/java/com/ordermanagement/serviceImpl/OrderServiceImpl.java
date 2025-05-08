package com.ordermanagement.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordermanagement.model.CustomResponse;
import com.ordermanagement.model.OrderItems;
import com.ordermanagement.model.Orders;
import com.ordermanagement.model.Products;
import com.ordermanagement.repo.OrdersRepository;
import com.ordermanagement.repo.ProductsRepository;
import com.ordermanagement.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public OrderServiceImpl(OrdersRepository ordersRepository, ProductsRepository productsRepository) {
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public CustomResponse createOrder(Orders order) {
        CustomResponse response = new CustomResponse();
        try {
            double totalAmount = 0;

            for (OrderItems item : order.getOrderItems()) {
                item.setOrderId(order.getId());
                item.setTotalPrice(item.getQuantity() * item.getUnitPrice());
                totalAmount += item.getTotalPrice();

                Products product = productsRepository.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
                product.setStock(product.getStock() - item.getQuantity());
//                productsRepository.save(product);
            }

            order.setAmount(totalAmount);
            Orders savedOrder = ordersRepository.save(order);

            String message = String.format(
                "{\"customerName\":\"%s\", \"email\":\"%s\", \"orderDate\":\"%s\", \"deliveryDate\":\"%s\", \"totalAmount\":%.2f}",
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getOrderDate(),
                savedOrder.getDeliveryDate(),
                savedOrder.getAmount()
            );

            kafkaTemplate.send(topic, message);
            logger.info("Order data published to kafka");
            response.setStatusCode(201);
            response.setSuccess(true);
            response.setMessage("Order created successfully");
        } catch (Exception e) {
        	e.printStackTrace();
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Failed to create order");
        }
        return response;
    }

    @Override
    public CustomResponse getOrderById(String orderId) {
        CustomResponse response = new CustomResponse();
        try {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            response.setStatusCode(200);
            response.setSuccess(true);
            response.setMessage("Order fetched successfully");
            response.setData(order);
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setSuccess(false);
            response.setMessage("Order not found");
        }
        return response;
    }

    @Override
    public CustomResponse getAllOrders() {
    	CustomResponse response = new CustomResponse();
    	List<Orders> orders=  ordersRepository.findAll();
    	if(!orders.isEmpty()) {
        	response.setStatusCode(200);
            response.setSuccess(true);
            response.setMessage("Order fetched successfully");
            response.setDetails(orders);
    	} else {
    		response.setStatusCode(404);
            response.setSuccess(false);
            response.setMessage("No Data Found");
    	}
    	return response;
    }

    @Override
    @Transactional
    public CustomResponse cancelOrder(String orderId) {
        CustomResponse response = new CustomResponse();
        try {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if (order.isCancelled()) {
                throw new IllegalStateException("Order already cancelled");
            }

            order.setCancelled(true);
            order.setCancelledOn(LocalDateTime.now().toString());

            for (OrderItems item : order.getOrderItems()) {
                Products product = productsRepository.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found for restocking"));
                product.setStock(product.getStock() + item.getQuantity());
                productsRepository.save(product);
            }

            ordersRepository.save(order);

            response.setStatusCode(200);
            response.setSuccess(true);
            response.setMessage("Order cancelled successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Failed to cancel order");
        }
        return response;
    }

    @Override
    @Transactional
    public CustomResponse updateOrder(String orderId, Orders updatedOrder) {
        CustomResponse response = new CustomResponse();
        try {
            Orders existingOrder = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setEmail(updatedOrder.getEmail());
            existingOrder.setDeliveryDate(updatedOrder.getDeliveryDate());

            ordersRepository.save(existingOrder);

            response.setStatusCode(200);
            response.setSuccess(true);
            response.setMessage("Order updated successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setSuccess(false);
            response.setMessage("Failed to update order");
        }
        return response;
    }
}
