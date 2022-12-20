package com.kodlamaio.rentalService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InvoiceCreatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceProducer.class);

	private NewTopic topic;
	
	private KafkaTemplate<String, InvoiceCreatedEvent> kafkaTemplateCreate;
	
 	public void sendMessage(InvoiceCreatedEvent event) {          
		LOGGER.info(String.format("Rental created event => %s", event.toString()));
		
		Message<InvoiceCreatedEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();
		
		kafkaTemplateCreate.send(message);
	}
}
