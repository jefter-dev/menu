package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exceptions.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void searchByName() throws InvalidUserException {
        try {
            User user = userRepository.findById(302L).orElse(null); // USER ADMIN
            // System.out.println("USER: " + user);

            Optional<Product> productsFilter = productService.searchByName("Paçoca", user);
            System.out.println(productsFilter);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}