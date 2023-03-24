package nl.plinio.backend.endpoints.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.endpoints.cart.model.CartDto;
import nl.plinio.backend.endpoints.cartitem.CartItemService;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.endpoints.cartitem.model.CartItemDto;
import nl.plinio.backend.endpoints.product.ProductService;
import nl.plinio.backend.endpoints.product.model.Product;
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
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartHelper cartHelper;

    public Cart findCart(UUID id) {
        return cartRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Cart.class));
    }

    public Page<CartDto> getAllCarts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<CartDto> cartResponseList = new ArrayList<>();
        Page<Cart> existingCarts = cartRepository.findAll(pageable);
        existingCarts.forEach(cart -> cartResponseList.add(new CartDto(cart)));
        return new PageImpl<>(cartResponseList, pageable, existingCarts.getTotalElements());
    }

    public CartDto createCart(Cart cart) {
        return new CartDto(cartRepository.save(cart));
    }

    public CartDto updateCart(UUID id, Cart cart) {
        Cart existingCart = findCart(id);
        existingCart.setAccount(cart.getAccount());
        return new CartDto(cartRepository.save(existingCart));
    }

    public void deleteCart(UUID id) {
        try {
            cartRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Cart.class);
        }
    }

    public void emptyCart() {
        cartItemService.deleteAllByCartId(cartHelper.getCartId());
    }

    public List<CartItemDto> getAllCartItems() {
        List<CartItem> existingCartItems = cartItemService.getAllByCartId(cartHelper.getCartId());
        List<CartItemDto> items = new ArrayList<>();
        existingCartItems.forEach(item -> items.add(new CartItemDto(item)));
        return items;
    }

    public CartItemDto addProductToCart(UUID productId) {
        Product product = productService.findProduct(productId);
        CartItem cartItem = cartItemService.findCartItemByProductId(productId);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            return cartItemService.updateCartItem(cartItem.getId(), cartItem);
        }

        cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(product);
        cartItem.setCart(cartHelper.getCart());
        return cartItemService.createCartItem(cartItem);
    }

    public void deleteProductFromCart(UUID productId) {
        CartItem cartItem = cartItemService.findCartItemByProductId(productId);
        cartItemService.deleteCartItem(cartItem.getId());
    }
}
