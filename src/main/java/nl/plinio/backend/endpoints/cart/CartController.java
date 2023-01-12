package nl.plinio.backend.endpoints.cart;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.endpoints.cart.model.CartDto;
import nl.plinio.backend.endpoints.item.model.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CartDto> getAllCarts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return cartService.getAllCarts(page, size, sort);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCart(@PathVariable UUID id) {
        return new CartDto(cartService.findCart(id));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto updateCart(@PathVariable UUID id, @RequestBody Cart cart) {
        return cartService.updateCart(id, cart);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable UUID id) {
        cartService.deleteCart(id);
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @DeleteMapping("/empty")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void emptyCart() {
        cartService.emptyCart();
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @PostMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto addItemToCart(@PathVariable UUID productId) {
        return cartService.addItemToCart(productId);
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromCart(@PathVariable UUID id) {
        cartService.deleteItemFromCart(id);
    }
}
