package com.ordermanagement.service;

import com.ordermanagement.model.CustomResponse;
import com.ordermanagement.model.Products;

public interface ProductService {
	CustomResponse addProduct(Products product);
    CustomResponse updateProduct(Products product);
    CustomResponse getProductById(String id);
    CustomResponse getAllProducts();
    CustomResponse deleteProduct(String id);
}
