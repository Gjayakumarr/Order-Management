package com.ordermanagement.users.microservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.users.microservice.model.Products;
import com.ordermanagement.users.microservice.service.ProductService;
import com.ordermanagement.users.response.CustomResponse;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public CustomResponse addProduct(@RequestBody Products product) {
        return productService.addProduct(product);
    }

    @PutMapping("/update")
    public CustomResponse updateProduct(@RequestBody Products product) {
        return productService.updateProduct(product);
    }

    @GetMapping("/get/{id}")
    public CustomResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getAll")
    public CustomResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/delete/{id}")
    public CustomResponse deleteProduct(@PathVariable String id) {
       return productService.deleteProduct(id);
    }
}
