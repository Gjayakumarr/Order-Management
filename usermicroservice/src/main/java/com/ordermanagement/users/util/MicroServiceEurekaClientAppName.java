package com.ordermanagement.users.util;

public enum MicroServiceEurekaClientAppName {
	
	NOTIFICATION("ms-notification"),
	ORDERS("ms-ordermanagement");
	public String name;

	private MicroServiceEurekaClientAppName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
