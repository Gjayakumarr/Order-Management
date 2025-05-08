package com.ordermanagement.users.microservice.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.ordermanagement.users.microservice.model.Orders;
import com.ordermanagement.users.microservice.service.Orderservice;
import com.ordermanagement.users.response.CustomResponse;
import com.ordermanagement.users.util.MicroServiceEurekaClientAppName;

@Service
public class OrderServiceImpl implements Orderservice {
	
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	private static final String Orders = "/api/orders";
	
	@Autowired
	private RestTemplate restTemplate;
	
	private EurekaClient eurekaClient;
	
	public OrderServiceImpl(EurekaClient client) {
		this.eurekaClient = client;
	}
	
	private String getMicroServiceBaseURL() {
		logger.debug("Fetching instance information from Eureka client.");
		InstanceInfo instanceInfo = eurekaClient
				.getApplication(MicroServiceEurekaClientAppName.ORDERS.name).getInstances().get(0);
		String baseUrl = instanceInfo.getHomePageUrl();
		logger.info("Microservice instance found at URL: {}", baseUrl);
		return baseUrl;
	}

	@Override
	public CustomResponse createOrder(Orders order) {
		String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
		String url = microServiceTaskManagementBaseURL + Orders + "/create";
		logger.info("Post calling URL :: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Orders> requestEntity = new HttpEntity<Orders>(order, headers);

		ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				CustomResponse.class);
		return addedRes.getBody();
	}

	@Override
	public CustomResponse updateOrder(Orders updatedOrder) {
		String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
		String url = microServiceTaskManagementBaseURL + Orders + "/update";
		logger.info("Post calling URL :: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Orders> requestEntity = new HttpEntity<Orders>(updatedOrder, headers);

		ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
				CustomResponse.class);
		return addedRes.getBody();
	}

	@Override
	public CustomResponse cancelOrder(String orderId) {
		String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
		String url = microServiceTaskManagementBaseURL + Orders + "/cancel/" + orderId;
		logger.info("Post calling URL :: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity requestEntity = new HttpEntity<>(headers);
		ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
				CustomResponse.class);
		return addedRes.getBody();
	}

	@Override
	public CustomResponse getOrderById(String orderId) {
		String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
		String url = microServiceTaskManagementBaseURL + Orders + "/getById/" + orderId;
		logger.info("Post calling URL :: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity requestEntity = new HttpEntity<>(headers);
		ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CustomResponse.class);
		return addedRes.getBody();
	}

	@Override
	public CustomResponse getAllOrders() {
		String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
		String url = microServiceTaskManagementBaseURL + Orders + "/getAllOerders";
		logger.info("Post calling URL :: " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity requestEntity = new HttpEntity<>(headers);
		ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CustomResponse.class);
		return addedRes.getBody();
	}

}
