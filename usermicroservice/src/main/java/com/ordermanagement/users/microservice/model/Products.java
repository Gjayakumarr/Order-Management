package com.ordermanagement.users.microservice.model;

public class Products {
	private String id;
	private String name;
	private String description;
	private String category;
	private int stock;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", stock=" + stock + "]";
	}

}
