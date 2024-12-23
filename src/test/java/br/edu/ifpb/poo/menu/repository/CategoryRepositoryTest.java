package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.CategoryRepository;
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
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveCategory() {
        User userAdmin = userRepository.findById(302L).orElse(null); // USER ADMIN
        assertNotNull(userAdmin, "Category should not be null");
//        System.out.println(userAdmin); // DEBUG

        Category category = new Category("Pizza", userAdmin);
        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory.getId());
        assertEquals("Pizza", savedCategory.getName());
    }

    @Test
    public void shouldGetCategory() {
        Category category = categoryRepository.findById(2L).orElse(null); // USER ADMIN
        assertNotNull(category, "Category should not be null");
        System.out.println(category); // DEBUG
    }

    @Test
    public void shouldListCategoriesByUser() {
        // Supondo que o usuário com ID 1 já existe e possui produtos no banco
        Long userId = 302L;

        // Busque todos os produtos associados ao usuário
        List<Category> categories = categoryRepository.findByUserId(userId);
        System.out.println(categories);
    }
}
