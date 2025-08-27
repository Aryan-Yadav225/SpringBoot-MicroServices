package com.aryan.microservices.order.service;

import com.aryan.microservices.order.dto.OrderRequest;
import com.aryan.microservices.order.model.Order;
import com.aryan.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        //map OrderRequest to Order entity
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());

        // save order in the database
        orderRepository.save(order);
        log.info("Order {} is saved", order.getId());
    }
}
