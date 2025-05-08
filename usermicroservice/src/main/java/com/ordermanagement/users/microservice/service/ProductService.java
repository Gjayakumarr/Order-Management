package com.ordermanagement.users.microservice.service;

import com.ordermanagement.users.microservice.model.Products;
import com.ordermanagement.users.response.CustomResponse;

public interface ProductService {
	CustomResponse addProduct(Products product);
    CustomResponse updateProduct(Products product);
    CustomResponse getProductById(String id);
    CustomResponse getAllProducts();
    CustomResponse deleteProduct(String id);
}
