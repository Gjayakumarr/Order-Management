package com.ordermanagement.users.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {
    private int statusCode;
    private String message;
    private boolean success;
    private ArrayList<Object> details;
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
	public ArrayList<Object> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<Object> details) {
		this.details = details;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "GenericResponse [statusCode=" + statusCode + ", message=" + message + ", success=" + success
				+ ", details=" + details + ", data=" + data + "]";
	}
    
}
