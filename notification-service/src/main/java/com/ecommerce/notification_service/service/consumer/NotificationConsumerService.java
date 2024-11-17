package com.ecommerce.notification_service.service.consumer;

import com.ecommerce.notification_service.dto.InventoryNotificationDTO;
import com.ecommerce.notification_service.dto.OrderNotificationDTO;
import com.ecommerce.notification_service.service.producer.NotificationProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumerService.class);
    private final NotificationProducerService notificationProducerService;
    private final ObjectMapper objectMapper;

    public NotificationConsumerService(NotificationProducerService notificationProducerService, ObjectMapper objectMapper) {
        this.notificationProducerService = notificationProducerService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-topic", groupId = "notification-service-group")
    public void consumeOrderNotification(String message) {
        try {
            OrderNotificationDTO orderNotification = objectMapper.readValue(message, OrderNotificationDTO.class);

            // Processar a notificação
            System.out.println("Notificação recebida:");
            System.out.println("ID: " + orderNotification.getOrderId());
            System.out.println("Produto: " + orderNotification.getProductName());
            System.out.println("Quantidade: " + orderNotification.getQuantity());
            System.out.println("Status: " + orderNotification.getStatus());

            InventoryNotificationDTO inventoryNotification = new InventoryNotificationDTO(
                orderNotification.getOrderId(),
                orderNotification.getProductName(),
                orderNotification.getQuantity()
            );

            notificationProducerService.sendInventoryNotification(inventoryNotification);
            logger.info("Mensagem processada e enviada para inventory-topic");

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: {}", message, e);
        }
    }

}
