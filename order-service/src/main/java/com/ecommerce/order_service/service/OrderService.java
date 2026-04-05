package com.ecommerce.order_service.service;


import com.ecommerce.order_service.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(Long id);

    OrderDto cancelOrder(Long id);
}
