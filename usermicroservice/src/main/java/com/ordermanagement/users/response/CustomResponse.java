package com.ordermanagement.users.response;

import java.util.ArrayList;
import java.util.List;

import com.ordermanagement.users.microservice.model.Orders;
import com.ordermanagement.users.microservice.model.Products;

public class CustomResponse {
	
	private int statusCode;
    private String message;
    private boolean success;
    private List<Orders> details;
    private List<Products> allProducts;
    private Object data;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<Orders> getDetails() {
		return details;
	}
	public void setDetails(List<Orders> details) {
		this.details = details;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public List<Products> getAllProducts() {
		return allProducts;
	}
	public void setAllProducts(List<Products> allProducts) {
		this.allProducts = allProducts;
	}
	@Override
	public String toString() {
		return "CustomResponse [statusCode=" + statusCode + ", message=" + message + ", success=" + success
				+ ", details=" + details + ", allProducts=" + allProducts + ", data=" + data + "]";
	}

}

