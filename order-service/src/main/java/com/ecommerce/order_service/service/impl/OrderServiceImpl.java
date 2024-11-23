package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.OrderNotificationDTO;
import com.ecommerce.order_service.dto.OrderRequestDTO;
import com.ecommerce.order_service.dto.OrderResponseDTO;
import com.ecommerce.order_service.exception.OrderNotFoundException;
import com.ecommerce.order_service.mapper.OrderNotificationMapper;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;
import com.ecommerce.order_service.service.producer.OrderProducerService;
import com.ecommerce.order_service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderNotificationMapper orderNotificationMapper;
    private final OrderProducerService orderProducerService;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        try {
            Order order = orderMapper.toEntity(request);
            Order savedOrder = orderRepository.save(order);

            OrderNotificationDTO notificationDTO = orderNotificationMapper.toDTO(savedOrder);
            orderProducerService.sendOrderNotification(notificationDTO);

            return orderMapper.toDTO(savedOrder);
        } catch (Exception e) {
            logger.error("Erro ao criar pedido: ", e);
            throw new RuntimeException("Erro ao criar pedido", e);
        }
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

}
