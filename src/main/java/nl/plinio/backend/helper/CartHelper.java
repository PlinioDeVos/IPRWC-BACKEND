package nl.plinio.backend.helper;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.cart.model.Cart;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartHelper {
    private final RelationHelper relationHelper;

    public Cart getCart() {
        return relationHelper.getAccount().getCart();
    }

    public UUID getCartId() {
        return getCart().getId();
    }
}
