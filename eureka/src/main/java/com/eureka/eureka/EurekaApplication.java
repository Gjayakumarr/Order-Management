package com.eureka.eureka;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.event.EventListener;

import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {

    @Autowired
    private Environment environment;

    private List<String> allowedIPs;

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

    @PostConstruct
    public void checkAllowedIPs() {
        allowedIPs = Arrays.asList(environment.getProperty("eureka.server.allowed-ips").split(","));
    }

    @EventListener
    public void handleEurekaRegistryEvent(EurekaRegistryAvailableEvent event) {
    System.out.println("Eureka registry available.");
    }

    @EventListener
    public void handleEurekaInstanceRegisteredEvent(EurekaInstanceRegisteredEvent event) {
        String instanceId = event.getInstanceInfo().getInstanceId();
        String ipAddress = event.getInstanceInfo().getIPAddr();
        System.out.println("Microservice with instanceId: " + instanceId + " and IP address: " + ipAddress + " is attempting to register with Eureka server.");

        String serverIPAddress = getServerIPAddress();
        
        if (!allowedIPs.contains(ipAddress)&&!ipAddress.equals(serverIPAddress)) {
            throw new SecurityException("Invalid Eureka server configuration. Restricted IP detected.");
        }
    }
    
    private String getServerIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to retrieve server IP address", e);
        }
    }

}
