package com.ordermanagement.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ordermanagement.model.Orders;

@Repository
public interface OrdersRepository extends MongoRepository<Orders, String> {
}
