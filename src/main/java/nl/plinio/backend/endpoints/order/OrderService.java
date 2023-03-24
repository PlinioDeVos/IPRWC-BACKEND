package nl.plinio.backend.endpoints.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.cartitem.CartItemService;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.endpoints.order.model.Order;
import nl.plinio.backend.endpoints.order.model.OrderDto;
import nl.plinio.backend.endpoints.orderitem.OrderItemService;
import nl.plinio.backend.endpoints.orderitem.model.OrderItem;
import nl.plinio.backend.exception.EnitityNotFoundException;
import nl.plinio.backend.helper.CartHelper;
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
    private final CartItemService cartItemService;
    private final OrderItemService orderItemService;
    private final CartHelper cartHelper;
    
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
        // Get the cart of the account and set the ID.
        order.setCartId(cartHelper.getCartId());

        // Create the order and get all cartItems.
        Order newOrder = orderRepository.save(order);
        List<CartItem> cartItems = cartItemService.getAllByCartId(order.getCartId());

        // Empty cart.
        cartItemService.deleteAllByCartId(order.getCartId());

        // Convert the cartItems to orderItems.
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemService.newOrderItemFromCartItem(cartItem);
            orderItem.setOrder(newOrder);
            orderItemService.createOrderItem(orderItem);
        }

        return new OrderDto(newOrder);
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
