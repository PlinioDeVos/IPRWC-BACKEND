package nl.plinio.backend.endpoints.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.order.model.Order;
import nl.plinio.backend.endpoints.order.model.OrderDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    
    public Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Order.class));
    }

    public Page<OrderDto> getAllOrders(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<OrderDto> orderResponseList = new ArrayList<>();
        Page<Order> existingOrders = orderRepository.findAll(pageable);
        existingOrders.forEach(order -> orderResponseList.add(new OrderDto(order)));
        return new PageImpl<>(orderResponseList, pageable, existingOrders.getTotalElements());
    }

    public OrderDto createOrder(Order order) {
        return new OrderDto(orderRepository.save(order));
    }

    public OrderDto updateOrder(UUID id, Order order) {
        Order existingOrder = findOrder(id);
        existingOrder.setCartId(order.getCartId());
        return new OrderDto(orderRepository.save(existingOrder));
    }

    public void deleteOrder(UUID id) {
        try {
            orderRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Order.class);
        }
    }
}
