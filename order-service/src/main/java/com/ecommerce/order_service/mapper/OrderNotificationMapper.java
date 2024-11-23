package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.OrderNotificationDTO;
import com.ecommerce.order_service.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationMapper {

    public OrderNotificationDTO toDTO(Order order) {
        return OrderNotificationDTO.builder()
            .orderId(order.getId())
            .productName(order.getProductName())
            .quantity(order.getQuantity())
            .status(order.getStatus())
            .build();
    }

}
