package com.ecommerce.order_service.service.producer;

import com.ecommerce.order_service.dto.OrderNotificationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducerService {

    private static final Logger logger = LoggerFactory.getLogger(OrderProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    public void sendOrderNotification(OrderNotificationDTO notificationMessage) {
        try {
            String topic = "order-topic";
            String message = objectMapper.writeValueAsString(notificationMessage);
            kafkaTemplate.send(topic, message);
            logger.info("Mensagem enviada para o tópico order-topic do Kafka: {}", message);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para o Kafka: {}", notificationMessage, e);
        }
    }
}
