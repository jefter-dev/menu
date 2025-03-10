package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CategoryServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    void getCategoriesWithProductsByUserId() {
        try {
            User user = new User(); // USER ADMIN
            user.setId(302L);

            List<Category> categories = categoryService.getCategoriesWithProductsByUserId(user);
            System.out.println(categories);

            // Exibe as categorias e seus produtos associados
            categories.forEach(category -> {
                System.out.println(category.toStringWithProducts());
            });
        } catch (InvalidUserException e) {
            System.out.println(e.getMessage());
            // throw new RuntimeException(e);
        }
    }
}