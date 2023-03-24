package nl.plinio.backend.endpoints.cartitem;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.endpoints.cartitem.model.CartItemDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@PreAuthorize("hasAuthority('admin')")
@RestController
@RequestMapping("cartitem")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CartItemDto> getAllCartItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return cartItemService.getAllCartItems(page, size, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto getCartItem(@PathVariable UUID id) {
        return new CartItemDto(cartItemService.findCartItem(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto createCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.createCartItem(cartItem);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto updateCartItem(@PathVariable UUID id, @RequestBody CartItem cartItem) {
        return cartItemService.updateCartItem(id, cartItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable UUID id) {
        cartItemService.deleteCartItem(id);
    }
}
