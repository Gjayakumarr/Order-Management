package com.ordermanagement.users.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ordermanagement.users.Model.UserDetailsV1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private int statusCode;
    private String message;
    private boolean success;
    private ArrayList<UserDetailsV1> userDetails;
    private List<Map<String, Object>> listOfUsers;
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
	public ArrayList<UserDetailsV1> getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(ArrayList<UserDetailsV1> userDetails) {
		this.userDetails = userDetails;
	}
	public List<Map<String, Object>> getListOfUsers() {
		return listOfUsers;
	}
	public void setListOfUsers(List<Map<String, Object>> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "UserResponse [statusCode=" + statusCode + ", message=" + message + ", success=" + success
				+ ", userDetails=" + userDetails + ", listOfUsers=" + listOfUsers + ", data=" + data + "]";
	}
    
}

