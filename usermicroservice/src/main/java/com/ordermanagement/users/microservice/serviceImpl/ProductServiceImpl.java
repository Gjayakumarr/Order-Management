package com.ordermanagement.users.microservice.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.ordermanagement.users.microservice.model.Products;
import com.ordermanagement.users.microservice.service.ProductService;
import com.ordermanagement.users.response.CustomResponse;
import com.ordermanagement.users.util.MicroServiceEurekaClientAppName;

@Service
public class ProductServiceImpl implements ProductService{
	 private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
		private static final String Products = "/api/products";
		
		@Autowired
		private RestTemplate restTemplate;
		
		@Autowired
		private EurekaClient eurekaClient;
		
		private String getMicroServiceBaseURL() {
			logger.debug("Fetching instance information from Eureka client.");
			InstanceInfo instanceInfo = eurekaClient
					.getApplication(MicroServiceEurekaClientAppName.ORDERS.name).getInstances().get(0);
			String baseUrl = instanceInfo.getHomePageUrl();
			logger.info("Microservice instance found at URL: {}", baseUrl);
			return baseUrl;
		}

		@Override
		public CustomResponse addProduct(Products products) {
			String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
			String url = microServiceTaskManagementBaseURL + Products + "/add";
			logger.info("Post calling URL :: " + url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Products> requestEntity = new HttpEntity<Products>(products, headers);

			ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					CustomResponse.class);
			return addedRes.getBody();
		}

		@Override
		public CustomResponse updateProduct(Products updatedProduct) {
			String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
			String url = microServiceTaskManagementBaseURL + Products + "/update";
			logger.info("Post calling URL :: " + url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Products> requestEntity = new HttpEntity<Products>(updatedProduct, headers);

			ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
					CustomResponse.class);
			return addedRes.getBody();
		}

		@Override
		public CustomResponse deleteProduct(String productId) {
			String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
			String url = microServiceTaskManagementBaseURL + Products + "/delete/" + productId;
			logger.info("Post calling URL :: " + url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity requestEntity = new HttpEntity<>(headers);
			ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
					CustomResponse.class);
			return addedRes.getBody();
		}

		@Override
		@Cacheable(value = "CustomResponse", key = "#productId")
		public CustomResponse getProductById(String productId) {
			String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
			String url = microServiceTaskManagementBaseURL + Products + "/getById/" + productId;
			logger.info("Post calling URL :: " + url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity requestEntity = new HttpEntity<>(headers);
			ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					CustomResponse.class);
			return addedRes.getBody();
		}

		@Override
		public CustomResponse getAllProducts() {
			String microServiceTaskManagementBaseURL = getMicroServiceBaseURL();
			String url = microServiceTaskManagementBaseURL + Products + "/getAllProducts";
			logger.info("Post calling URL :: " + url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity requestEntity = new HttpEntity<>(headers);
			ResponseEntity<CustomResponse> addedRes = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					CustomResponse.class);
			return addedRes.getBody();
		}
}
