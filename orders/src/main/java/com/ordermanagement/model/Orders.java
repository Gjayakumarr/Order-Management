package com.ordermanagement.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("orders")
public class Orders {
	
	@Id
	private String id;
	private String customerName;
	private String email;
	private String phone;
	private String shippingAddress;
	private String productId;
	private String productName;
	private int quantity;
	private double amount;
	private String orderDate;
	private String status;
	private String deliveryDate;
	private String paymentMethod;
	private boolean isCancelled;
	private String cancelledOn;
	
    private List<OrderItems> orderItems;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public List<OrderItems> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	public String getCancelledOn() {
		return cancelledOn;
	}
	public void setCancelledOn(String cancelledOn) {
		this.cancelledOn = cancelledOn;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", customerName=" + customerName + ", email=" + email + ", phone=" + phone
				+ ", shippingAddress=" + shippingAddress + ", productId=" + productId + ", productName=" + productName
				+ ", quantity=" + quantity + ", amount=" + amount + ", orderDate=" + orderDate + ", status=" + status
				+ ", deliveryDate=" + deliveryDate + ", paymentMethod=" + paymentMethod + ", isCancelled=" + isCancelled
				+ ", cancelledOn=" + cancelledOn + ", orderItems=" + orderItems + "]";
	}
	
}
