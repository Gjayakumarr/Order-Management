package com.ordermanagement.users.security.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import org.apache.kafka.common.serialization.StringSerializer;
import com.ordermanagement.users.Model.ApiLog;

@Configuration
@ConditionalOnProperty(name = "kafkalogger.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServersConfig;

//	@Bean
//	public KafkaTemplate<byte[], byte[]> kafkaTemplate(ProducerFactory<byte[], byte[]> producerFactory) {
//		return new KafkaTemplate<>(producerFactory);
//	}

	@Bean
	public KafkaTemplate<String, ApiLog> kafkaTemplate() {
	    Map<String, Object> producerProps = new HashMap<>();
	    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
	    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ApiLogReqResSerializer.class);
	    ProducerFactory<String, ApiLog> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
	    return new KafkaTemplate<>(producerFactory);
	}



}