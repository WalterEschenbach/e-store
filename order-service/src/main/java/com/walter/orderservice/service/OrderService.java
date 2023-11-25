package com.walter.orderservice.service;

import com.walter.orderservice.dto.OrderLineItemsDto;
import com.walter.orderservice.dto.OrderRequest;
import com.walter.orderservice.model.Order;
import com.walter.orderservice.model.OrderLineItems;
import com.walter.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLindItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLindItemsDto.getPrice());
        orderLineItems.setQuantity(orderLindItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLindItemsDto.getSkuCode());

        return orderLineItems;
    }
}
