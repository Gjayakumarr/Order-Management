package com.ordermanagement.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ordermanagement.model.Products;

@Repository
public interface ProductsRepository extends MongoRepository<Products, String> {
}