package com.notification.notification;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.notification")
public class MsNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotificationApplication.class, args);
	}

	@Bean
	public JavaMailSender javaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setUsername("jaykumarasti@gmail.com");
	    mailSender.setPassword("rtsg njos yxju dhey");
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    return mailSender;
	}
}
