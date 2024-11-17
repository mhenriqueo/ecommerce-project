package com.ecommerce.notification_service.service.producer;

import com.ecommerce.notification_service.dto.InventoryNotificationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NotificationProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendInventoryNotification(InventoryNotificationDTO inventoryNotificationMessage) {
        try {
            String topic = "inventory-topic";
            String message = objectMapper.writeValueAsString(inventoryNotificationMessage);
            kafkaTemplate.send(topic, message);
            logger.info("Mensagem enviada para o tópico inventory-topic do Kafka: {}", message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para o Kafka: {}", inventoryNotificationMessage, e);
        }
    }

}
