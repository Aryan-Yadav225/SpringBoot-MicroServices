package com.aryan.microservices.order.service;

import com.aryan.microservices.order.client.InventoryClient;
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
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        // check if the product is in stock
        boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (inStock) {
            //map OrderRequest to Order entity
            var order = mapToOrder(orderRequest);
            // save order in the database
            orderRepository.save(order);
            log.info("Order {} is saved", order.getId());
        }else{
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }

}
