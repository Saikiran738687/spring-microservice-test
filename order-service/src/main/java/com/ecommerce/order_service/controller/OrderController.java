package com.ecommerce.order_service.controller;


import com.ecommerce.order_service.dto.OrderDto;
import com.ecommerce.order_service.service.OrderService;
import com.ecommerce.order_service.util.ResponseStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ================= CREATE ORDER =================
    @PostMapping(
            consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<ResponseStructure<OrderDto>> createOrder(
            @RequestBody OrderDto orderDto) {

        OrderDto savedOrder = orderService.createOrder(orderDto);

        ResponseStructure<OrderDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setMessage("Order Created Successfully");
        structure.setData(savedOrder);

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    // ================= GET ALL =================
    @GetMapping(produces = { "application/json", "application/xml" })
    public ResponseEntity<ResponseStructure<List<OrderDto>>> getAllOrders() {

        List<OrderDto> orders = orderService.getAllOrders();

        ResponseStructure<List<OrderDto>> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Orders Fetched Successfully");
        structure.setData(orders);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // ================= GET BY ID =================
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<ResponseStructure<OrderDto>> getOrderById(
            @PathVariable Long id) {

        OrderDto order = orderService.getOrderById(id);

        ResponseStructure<OrderDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Order Found");
        structure.setData(order);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // ================= CANCEL ORDER =================
    @PutMapping(
            value = "/{id}/cancel",
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<ResponseStructure<OrderDto>> cancelOrder(
            @PathVariable Long id) {

        OrderDto order = orderService.cancelOrder(id);

        ResponseStructure<OrderDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("Order Cancelled Successfully");
        structure.setData(order);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}