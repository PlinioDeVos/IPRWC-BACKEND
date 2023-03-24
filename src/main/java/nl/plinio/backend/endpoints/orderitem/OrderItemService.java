package nl.plinio.backend.endpoints.orderitem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.endpoints.orderitem.model.OrderItem;
import nl.plinio.backend.endpoints.orderitem.model.OrderItemDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem findOrderItem(UUID id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(OrderItem.class));
    }

    public Page<OrderItemDto> getAllOrderItems(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<OrderItemDto> orderItemResponseList = new ArrayList<>();
        Page<OrderItem> existingOrders = orderItemRepository.findAll(pageable);
        existingOrders.forEach(order -> orderItemResponseList.add(new OrderItemDto(order)));
        return new PageImpl<>(orderItemResponseList, pageable, existingOrders.getTotalElements());
    }

    public OrderItemDto createOrderItem(OrderItem orderItem) {
        return new OrderItemDto(orderItemRepository.save(orderItem));
    }

    public OrderItemDto updateOrderItem(UUID id, OrderItem orderItem) {
        OrderItem existingOrderItem = findOrderItem(id);
        existingOrderItem.setUnitPrice(orderItem.getUnitPrice());
        existingOrderItem.setQuantity(orderItem.getQuantity());
        existingOrderItem.setProduct(orderItem.getProduct());
        return new OrderItemDto(orderItemRepository.save(existingOrderItem));
    }

    public void deleteOrderItem(UUID id) {
        try {
            orderItemRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(OrderItem.class);
        }
    }

    public OrderItem newOrderItemFromCartItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUnitPrice(cartItem.getProduct().getPrice());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setProduct(cartItem.getProduct());
        return orderItem;
    }
}
