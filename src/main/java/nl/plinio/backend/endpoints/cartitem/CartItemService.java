package nl.plinio.backend.endpoints.cartitem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.cartitem.model.CartItem;
import nl.plinio.backend.endpoints.cartitem.model.CartItemDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem findCartItem(UUID id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(CartItem.class));
    }

    public Page<CartItemDto> getAllCartItems(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<CartItemDto> cartItemResponseList = new ArrayList<>();
        Page<CartItem> existingCartItems = cartItemRepository.findAll(pageable);
        existingCartItems.forEach(item -> cartItemResponseList.add(new CartItemDto(item)));
        return new PageImpl<>(cartItemResponseList, pageable, existingCartItems.getTotalElements());
    }

    public List<CartItem> getAllByCartId(UUID cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }

    public CartItemDto createCartItem(CartItem cartItem) {
        return new CartItemDto(cartItemRepository.save(cartItem));
    }

    public CartItemDto updateCartItem(UUID id, CartItem cartItem) {
        CartItem existingCartItem = findCartItem(id);
        existingCartItem.setQuantity(cartItem.getQuantity());
        return new CartItemDto(cartItemRepository.save(existingCartItem));
    }

    public void deleteCartItem(UUID id) {
        try {
            cartItemRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(CartItem.class);
        }
    }

    public void deleteAllByCartId(UUID id) {
        cartItemRepository.deleteAllByCartId(id);
    }

    public CartItem findCartItemByProductId(UUID id) {
        return cartItemRepository.findByProductId(id);
    }
}
