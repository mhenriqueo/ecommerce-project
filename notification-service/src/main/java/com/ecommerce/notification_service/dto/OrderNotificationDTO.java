package com.ecommerce.notification_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderNotificationDTO {
    private Long orderId;
    private String productName;
    private int quantity;
    private String status;
}
