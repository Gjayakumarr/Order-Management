package com.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderNotificationService {

	 @Autowired
	 private EmailService emailService;

	 @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
	 public void consumeOrderNotification(String message) {
		 try {
			 ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(message);

	            String customerName = jsonNode.get("customerName").asText();
	            String email = jsonNode.get("email").asText();
	            String orderDate = jsonNode.get("orderDate").asText();
	            String deliveryDate = jsonNode.get("deliveryDate").asText();
	            double totalAmount = jsonNode.get("totalAmount").asDouble();

	            String subject = "Order Confirmation";
	            String body = String.format("Hello %s,\n\nYour order has been placed successfully!\n\nOrder Date: %s\nDelivery Date: %s\nTotal Amount: $%.2f\n\nThank you for shopping with us!",
	                    customerName, orderDate, deliveryDate, totalAmount);

	            emailService.sendEmail(email, subject, body);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }   
}
