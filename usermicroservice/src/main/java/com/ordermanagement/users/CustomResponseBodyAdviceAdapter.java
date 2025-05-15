package com.ordermanagement.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordermanagement.users.Model.ApiLog;
import com.ordermanagement.users.Model.EndPointRequestAndResponse;
import com.ordermanagement.users.security.config.MessageProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;

@Component
@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(CustomResponseBodyAdviceAdapter.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private MessageProducer messageProducer;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true; // Apply to all responses
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        try {
            ApiLog apiLog = new ApiLog();
            EndPointRequestAndResponse endpoint = new EndPointRequestAndResponse();

            // Build Request Info
            String method = httpServletRequest.getMethod();
            String uri = httpServletRequest.getRequestURI();

            endpoint.setEndReq(method + " " + uri);
            apiLog.setEndApi(uri);
            apiLog.setHost(httpServletRequest.getRemoteHost());
            apiLog.setUserAgent(httpServletRequest.getHeader("User-Agent"));
            apiLog.setDateTime(Instant.now().toEpochMilli());
            apiLog.setResponseCode(httpServletResponse.getStatus());
            apiLog.setSuccess(httpServletResponse.getStatus() < 400);
//            apiLog.setEndpoint(endpoint);
            apiLog.setRequestFormat(httpServletRequest.getContentType());

            String responseJson = objectMapper.writeValueAsString(body);
            endpoint.setEndRes(responseJson);

            messageProducer.sendMessage(apiLog);

            logger.info("KafkaLog: {}", apiLog);

        } catch (Exception e) {
            logger.error("Failed to log API response to Kafka", e);
        }

        return body;
    }
}

