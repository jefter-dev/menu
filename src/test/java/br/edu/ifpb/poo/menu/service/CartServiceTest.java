package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CartServiceTest {
    @Autowired
    CartService cartService;

    @Test
    void createCart() {
        try {
            Client client = new Client();
            client.setId(2L);

            Cart cart = cartService.createCart(new Cart(), client);
            System.out.println("CART: " + cart);
        } catch (Exception e) {
            System.out.println("ERROR [createCart]: " + e   .getMessage());
        }
    }

    @Test
    void getCartWithItemsAndAdditional() {
        try {
            Cart cartFind = new Cart();
            cartFind.setId(14L);

            Cart cart = cartService.findCartWithDetails(cartFind);
            if (cart != null) {
                System.out.println("CART: " + cart.toStringWithItems());
            }
        } catch (Exception e) {
            System.out.println("ERROR [getCartWithItemsAndAdditionals]: " + e   .getMessage());
        }
    }

    @Test
    void emptyCart() {
        try {
            Cart cartFind = new Cart();
            cartFind.setId(14L);

            cartService.emptyCart(cartFind);

            this.getCartWithItemsAndAdditional();
        } catch (Exception e) {
            System.out.println("ERROR [getCartWithItems]: " + e   .getMessage());
        }
    }
}