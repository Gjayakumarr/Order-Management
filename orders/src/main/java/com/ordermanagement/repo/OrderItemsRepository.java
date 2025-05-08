package com.ordermanagement.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ordermanagement.model.OrderItems;

@Repository
public interface OrderItemsRepository extends MongoRepository<OrderItems, String> {
}
