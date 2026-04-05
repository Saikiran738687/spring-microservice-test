package com.ecommerce.order_service.serviceImpl;

import com.ecommerce.order_service.dto.*;
import com.ecommerce.order_service.entity.*;
import com.ecommerce.order_service.feign.CustomerClient;
import com.ecommerce.order_service.feign.ProductClient;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    // ================= CREATE =================
    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        // ✅ STEP 1: Validate Customer
        Object customer = customerClient.getCustomerById(orderDto.getCustomerId());
        if (customer == null) {
            throw new RuntimeException("Customer Not Found");
        }

        Order order = convertToEntity(orderDto);

        // ✅ STEP 2: Defaults
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setStatus("CREATED");

        double totalAmount = 0.0;

        // ✅ STEP 3: Process items
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {

                // 🔥 Call Product Service
                ProductResponse product = productClient.getProductById(item.getProductId());

                if (product == null) {
                    throw new RuntimeException("Product Not Found");
                }

                // ✅ STOCK CHECK
                Integer stock = productClient.getStock(item.getProductId());

                if (stock < item.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + item.getProductId());
                }

                // ✅ REDUCE STOCK
                productClient.reduceStock(item.getProductId(), item.getQuantity());

                // ✅ PRICE LOGIC
                double price = product.getPrice();
                item.setPrice(price);

                double subTotal = price * item.getQuantity();
                item.setSubTotal(subTotal);

                totalAmount += subTotal;

                item.setOrder(order);
            }
        }

        // ✅ STEP 4: Total
        order.setTotalAmount(totalAmount);

        // ✅ STEP 5: Payment
        if (order.getPayment() != null) {
            order.getPayment().setAmount(totalAmount);
            order.getPayment().setPaymentStatus("PENDING");
            order.getPayment().setOrder(order);
        }

        Order savedOrder = orderRepository.save(order);

        return convertToDto(savedOrder);
    }

    // ================= GET ALL =================
    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ================= GET BY ID =================
    @Override
    public OrderDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        return convertToDto(order);
    }

    // ================= CANCEL =================
    @Override
    public OrderDto cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        // 🔥 RESTORE STOCK BEFORE CANCELLING
        if (!"CANCELLED".equals(order.getStatus())) {

            if (order.getOrderItems() != null) {
                for (OrderItem item : order.getOrderItems()) {

                    productClient.increaseStock(
                            item.getProductId(),
                            item.getQuantity()
                    );
                }
            }

            order.setStatus("CANCELLED");
        }

        Order savedOrder = orderRepository.save(order);

        return convertToDto(savedOrder);
    }

    // ================= DTO → ENTITY =================
    private Order convertToEntity(OrderDto dto) {

        Order order = new Order();
        order.setId(dto.getId());
        order.setCustomerId(dto.getCustomerId());

        if (dto.getOrderItems() != null) {
            List<OrderItem> items = dto.getOrderItems().stream().map(itemDto -> {
                OrderItem item = new OrderItem();
                item.setId(itemDto.getId());
                item.setProductId(itemDto.getProductId());
                item.setQuantity(itemDto.getQuantity());
                item.setOrder(order);
                return item;
            }).collect(Collectors.toList());

            order.setOrderItems(items);
        }

        if (dto.getPayment() != null) {
            Payment payment = new Payment();
            payment.setId(dto.getPayment().getId());
            payment.setPaymentMethod(dto.getPayment().getPaymentMethod());
            payment.setOrder(order);
            order.setPayment(payment);
        }

        return order;
    }

    // ================= ENTITY → DTO =================
    private OrderDto convertToDto(Order order) {

        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomerId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        if (order.getOrderItems() != null) {
            List<OrderItemDto> items = order.getOrderItems().stream().map(item -> {
                OrderItemDto itemDto = new OrderItemDto();
                itemDto.setId(item.getId());
                itemDto.setProductId(item.getProductId());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setPrice(item.getPrice());
                itemDto.setSubTotal(item.getSubTotal());
                return itemDto;
            }).collect(Collectors.toList());

            dto.setOrderItems(items);
        }

        if (order.getPayment() != null) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(order.getPayment().getId());
            paymentDto.setPaymentMethod(order.getPayment().getPaymentMethod());
            paymentDto.setPaymentStatus(order.getPayment().getPaymentStatus());
            paymentDto.setAmount(order.getPayment().getAmount());

            dto.setPayment(paymentDto);
        }

        return dto;
    }
}