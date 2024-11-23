package com.ecommerce.order_service.dto;

import com.ecommerce.order_service.model.enums.OrderStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderNotificationDTO {
    private Long orderId;
    private String productName;
    private int quantity;
    private OrderStatus status;
}
