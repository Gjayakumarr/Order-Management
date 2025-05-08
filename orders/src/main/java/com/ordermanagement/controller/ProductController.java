package com.ordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ordermanagement.model.CustomResponse;
import com.ordermanagement.model.Products;
import com.ordermanagement.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public CustomResponse addProduct(@RequestBody Products product) {
        return productService.addProduct(product);
    }

    @PutMapping("/update")
    public CustomResponse updateProduct(@RequestBody Products product) {
        return productService.updateProduct(product);
    }

    @GetMapping("/getById/{id}")
    public CustomResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getAllProducts")
    public CustomResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/delete/{id}")
    public CustomResponse deleteProduct(@PathVariable String id) {
       return productService.deleteProduct(id);
    }
}
