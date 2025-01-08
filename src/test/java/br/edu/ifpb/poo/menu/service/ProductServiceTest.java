package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
            User user = new User(); // USER ADMIN
            user.setId(302L);

            List<Product> productsFilter = productService.searchByNameForUser("Paçoca", user);
            System.out.println(productsFilter);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getProductWithCategoryForUser() {
        try {
            Product productFind = new Product(); // USER ADMIN
            productFind.setId(802L);
            // System.out.println("USER: " + user);

            Product product = productService.getProductDetailsWithCategory(productFind);
            System.out.println(product);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getProductById() {
        try {
            Product product = productService.getProductById(1104L);
            System.out.println(product);
//            System.out.println(product.getUser());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getProductsByUser() {
        try {
            User user = new User(); // USER ADMIN
            user.setId(302L);

            List<Product> products = productService.getProductsByUser(user);
            System.out.println(products);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}