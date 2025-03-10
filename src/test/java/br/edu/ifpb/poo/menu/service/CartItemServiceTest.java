package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CartItemServiceTest {
    @Autowired
    private CartItemService cartItemService;

    @Test
    void addItemCart() {
        try {
            Cart cart = new Cart();
            cart.setId(14L);

            Product product = new Product();
            product.setId(1104L);

            cartItemService.addItemCart(cart, product);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}