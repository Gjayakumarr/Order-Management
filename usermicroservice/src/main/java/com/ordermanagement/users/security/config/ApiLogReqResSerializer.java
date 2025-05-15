package com.ordermanagement.users.security.config;

import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordermanagement.users.Model.ApiLog;

public class ApiLogReqResSerializer implements Serializer<ApiLog> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, ApiLog data) {
		try {
			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new RuntimeException("Error serializing ApiLogInterceptorReqRes", e);
		}
	}
}
