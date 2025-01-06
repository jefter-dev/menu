package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MenuServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MenuService menuService;

    @Test
    void getCategoriesAllWithProductsByUser() {
        try {
            User user = userService.getUserById(302L); // USER ADMIN

            List<Category> categories = menuService.getCategoriesWithProductsForUser(user);
            System.out.println(categories);

            categories.forEach(category -> {
                System.out.println(category.toStringWithProducts());
            });

        } catch (UserNotFoundException | InvalidUserException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findProductByName() {
        try {
            User user = userService.getUserById(302L); // USER ADMIN
            String name = "PAÇOCA";

            List<Product> productsFind = menuService.findProduct(name, user);
            System.out.println(productsFind);

        } catch (UserNotFoundException | InvalidUserException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getUserDetails() {
        try {
            User userFind = new User();
            userFind.setId(302L);

            User user = menuService.getUserDetails(userFind); // USER ADMIN
            System.out.println(user);

        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}