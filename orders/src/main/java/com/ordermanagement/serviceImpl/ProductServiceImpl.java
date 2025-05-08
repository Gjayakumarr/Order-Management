package com.ordermanagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ordermanagement.model.CustomResponse;
import com.ordermanagement.model.Products;
import com.ordermanagement.repo.ProductsRepository;
import com.ordermanagement.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public CustomResponse addProduct(Products product) {
    	CustomResponse response = new CustomResponse();
	    try {
	    	productsRepository.save(product);
	        response.setStatusCode(201);
	        response.setSuccess(true);
	        response.setMessage("Product created successfully");
	    } catch (Exception e) {
	        response.setStatusCode(500);
	        response.setSuccess(false);
	        response.setMessage("Failed to create Product");
	    }
	    return response;
    }

    @Override
    public CustomResponse updateProduct(Products updatedProduct) {
    	CustomResponse response = new CustomResponse();
	    try {
	        Products existing = productsRepository.findById(updatedProduct.getId())
	            .orElseThrow(() -> new RuntimeException("Product not found with id: " + updatedProduct.getId()));
	        
	        existing.setName(updatedProduct.getName());
	        existing.setCategory(updatedProduct.getCategory());
	        existing.setDescription(updatedProduct.getDescription());
	        existing.setStock(updatedProduct.getStock());
	
	        productsRepository.save(existing);
	        response.setStatusCode(200);
	        response.setSuccess(true);
	        response.setMessage("Product updated successfully");
	    } catch (Exception e) {
	        response.setStatusCode(500);
	        response.setSuccess(false);
	        response.setMessage("Failed to update Product");
	    }
	    return response;
    }

    @Override
    @Cacheable(value = "CustomResponse", key = "#id", unless = "#operation == null")
    public CustomResponse getProductById(String id) {
    	CustomResponse response = new CustomResponse();
    	try {
	        Products products = productsRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
	        response.setStatusCode(200);
	        response.setSuccess(true);
	        response.setMessage("Product fetched successfully");
	        response.setData(products);
	    } catch (Exception e) {
	        response.setStatusCode(404);
	        response.setSuccess(false);
	        response.setMessage("Product not found");
	    }
	    return response;
    }

    @Override
    public CustomResponse getAllProducts() {
    	CustomResponse response = new CustomResponse();
        List<Products> products =  productsRepository.findAll();
        if(!products.isEmpty()) {
        	response.setStatusCode(200);
            response.setSuccess(true);
            response.setMessage("Product fetched successfully");
            response.setAllProducts(products);
    	} else {
    		response.setStatusCode(404);
            response.setSuccess(false);
            response.setMessage("No Data Found");
    	}
    	return response;
    }

    @Override
    public CustomResponse deleteProduct(String id) {
    	CustomResponse response = new CustomResponse();
    	try {
	        if (!productsRepository.existsById(id)) {
	            throw new RuntimeException("Product not found with id: " + id);
	        }
	        productsRepository.deleteById(id);
	        response.setStatusCode(200);
	        response.setSuccess(true);
	        response.setMessage("Product deleted successfully");
	    } catch (Exception e) {
	        response.setStatusCode(500);
	        response.setSuccess(false);
	        response.setMessage("Failed to delete Product");
	    }
	    return response;
    }
}
