package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exceptions.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.Category;
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

    @Test
    void getAllWithProductsByUser() {
        try {
            User user = userService.getUserById(302L); // USER ADMIN
            System.out.println("USUARIO SERVICE: " + user);

            List<Category> categories = categoryService.getAllWithProductsByUser(user);
            System.out.println(categories);

            categories.forEach(category -> {
                System.out.println(category.toStringWithProducts());
            });

        } catch (UserNotFoundException | InvalidUserException e) {
            System.out.println(e.getMessage());
        }
    }
}