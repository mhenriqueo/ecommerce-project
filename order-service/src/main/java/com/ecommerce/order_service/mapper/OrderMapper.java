package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.model.enums.OrderStatus;
import com.ecommerce.order_service.dto.OrderRequestDTO;
import com.ecommerce.order_service.dto.OrderResponseDTO;
import com.ecommerce.order_service.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO request) {
        return Order.builder()
            .productName(request.getProductName())
            .quantity(request.getQuantity())
            .price(request.getPrice())
            .status(OrderStatus.PENDING)
            .build();
    }

    public OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
            .id(order.getId())
            .productName(order.getProductName())
            .quantity(order.getQuantity())
            .price(order.getPrice())
            .status(order.getStatus())
            .orderDate(order.getOrderDate())
            .build();
    }

}
