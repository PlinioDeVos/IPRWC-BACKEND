package nl.plinio.backend.endpoints.orderitem;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.orderitem.model.OrderItem;
import nl.plinio.backend.endpoints.orderitem.model.OrderItemDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("orderitem")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('admin')")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderItemDto> getAllOrderItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return orderItemService.getAllOrderItems(page, size, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemDto getOrderItem(@PathVariable UUID id) {
        return new OrderItemDto(orderItemService.findOrderItem(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemDto createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.createOrderItem(orderItem);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemDto updateOrderItem(@PathVariable UUID id, @RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItem(id, orderItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable UUID id) {
        orderItemService.deleteOrderItem(id);
    }
}
