package com.ordermanagement.users.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import com.ordermanagement.users.Model.ApiLog;
import com.ordermanagement.users.Model.KafkaObject;

@Configuration
@PropertySource("classpath:application.properties")
@ConditionalOnProperty(name = "kafkalogger.enabled", havingValue = "true",matchIfMissing = false)
public class MessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	@Value("${spring.kafka.producer.topic}")
	private String logKafkaProducerTopic;

	@Autowired
	private KafkaTemplate<String, ApiLog> kafkaTemplate;

	public void sendMessage(ApiLog apiLogInterceptorReqRes) {
		try {
			logger.info("Attempting to send message to topic: {}", logKafkaProducerTopic);

			// Serialize ApiLogInterceptorReqRes to byte[]
			KafkaObject kafkaObject = new KafkaObject();
			kafkaObject.setApiLog(apiLogInterceptorReqRes);
			
			// Send serialized data to Kafka
			kafkaTemplate.send(logKafkaProducerTopic, apiLogInterceptorReqRes)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    logger.warn("Failed to send message to topic: {}. Error: {}", 
                    		logKafkaProducerTopic, ex.getMessage());
                } else if (result != null) {
                    logger.info("Message successfully sent to topic: {} at offset: {}",
                        result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
                }
            });
		} catch (KafkaException e) {
			logger.error("Kafka exception while sending message to topic: {}. Error: {}",
					logKafkaProducerTopic, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Unexpected error while sending message to topic: {}. Error: {}",
					logKafkaProducerTopic, e.getMessage());
			e.printStackTrace();
		}
	}

}
