package com.ecommerce.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryNotificationDTO {
    private Long orderId;
    private String productName;
    private int quantity;
}
