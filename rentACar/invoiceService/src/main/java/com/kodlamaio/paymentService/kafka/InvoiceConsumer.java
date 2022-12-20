package com.kodlamaio.paymentService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InvoiceCreatedEvent;
import com.kodlamaio.paymentService.business.abstracts.InvoiceService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceConsumer {
	private InvoiceService invoiceService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceConsumer.class);
	
	@KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "createInvoice"
    )
    public void consume(InvoiceCreatedEvent event){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        invoiceService.add(event);
    }
}
