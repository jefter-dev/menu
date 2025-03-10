package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.CartItem;
import br.edu.ifpb.poo.menu.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CartItemAdditionalServiceTest {
    @Autowired
    private CartItemAdditionalService cartItemAdditionalService;

    @Test
    void addItemAdditionalCart() {
        try {
            CartItem cartItem = new CartItem();
            cartItem.setId(12L);

            Additional additional = new Additional();
            additional.setId(4L);

            cartItemAdditionalService.addItemAdditionalCart(cartItem, additional, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}