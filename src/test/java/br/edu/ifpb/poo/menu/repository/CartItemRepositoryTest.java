package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CartItemRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldSaveCart() {
        Cart cart = cartRepository.findById(1L).orElse(null); // USER ADMIN
        System.out.println(cart);

        Product product = productRepository.findById(802L).orElse(null);

        CartItem newCartItem = new CartItem(cart, product, 1);
        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        assertNotNull(savedCartItem.getId());
    }

    @Test
    void findItemsWithAdditional() {
        cartItemRepository.findItemsWithProductsAndAdditional(14L);
    }
}
