package nl.plinio.backend.endpoints.order;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.order.model.Order;
import nl.plinio.backend.endpoints.order.model.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return orderService.getAllOrders(page, size, sort);
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrder(@PathVariable UUID id) {
        return new OrderDto(orderService.findOrder(id));
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto updateOrder(@PathVariable UUID id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }
}
