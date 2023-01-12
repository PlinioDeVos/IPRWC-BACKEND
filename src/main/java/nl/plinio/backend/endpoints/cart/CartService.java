package nl.plinio.backend.endpoints.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.endpoints.cart.model.CartDto;
import nl.plinio.backend.endpoints.item.ItemService;
import nl.plinio.backend.endpoints.item.model.Item;
import nl.plinio.backend.endpoints.item.model.ItemDto;
import nl.plinio.backend.endpoints.product.ProductService;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.exception.EnitityNotFoundException;
import nl.plinio.backend.helper.RelationHelper;
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
    private final ItemService itemService;
    private final RelationHelper relationHelper;

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
        existingCart.setFinalised(cart.isFinalised());
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
        List<Item> items = itemService.getAllByCartId(getAccountCart().getId());

        if (!items.isEmpty()) {
            for (Item item : items) {
                itemService.deleteItem(item.getId());
            }
        }
    }

    public List<ItemDto> getAllCartItems() {
        List<Item> existingItems = itemService.getAllByCartId(getAccountCart().getId());
        List<ItemDto> items = new ArrayList<>();
        existingItems.forEach(item -> items.add(new ItemDto(item)));
        return items;
    }

    public ItemDto addItemToCart(UUID productId) {
        final Product product = productService.findProduct(productId);
        Item item = new Item();
        item.setBuyPrice(product.getPrice()); // A buy price to ensure the order price will not change with the product price.
        item.setProduct(product);
        item.setCart(getAccountCart());

        return itemService.createItem(item);
    }

    public void deleteItemFromCart(UUID id) {
        itemService.deleteItem(id);
    }

    private Cart getAccountCart() {
        final Account account = relationHelper.getAccount();
        List<Cart> carts = cartRepository.findAllByAccountId(account.getId());

        for (Cart cart : carts) {
            if (!cart.isFinalised()) {
                return cart;
            }
        }

        Cart cart = new Cart();
        cart.setAccount(account);
        cart.setFinalised(false);
        return cartRepository.save(cart);
    }
}
