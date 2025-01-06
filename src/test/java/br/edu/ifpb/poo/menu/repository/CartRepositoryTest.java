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
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSaveCart() {
        Client client = clientRepository.findById(52L).orElse(null); // USER ADMIN
        System.out.println(client);

        Cart newCart = new Cart(client);
        System.out.println(newCart);

        Cart savedCart = cartRepository.save(newCart);

        assertNotNull(savedCart.getId());
    }

    @Test
    public void getFindByIdCart() {
        Cart cart = cartRepository.findById(1L).orElse(null); // USER ADMIN
        System.out.println(cart);
        assertNotNull(cart.getId());
    }

    @Test
    void findCartWithItemsAndProductsAndAdditional() {
        Cart cart = cartRepository.findCartWithItemsAndProductsAndAdditional(14L);

        System.out.println(cart.toStringWithItems());
        System.out.println("TOTAL: "+cart.getTotal());
    }
}