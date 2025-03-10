package br.edu.ifpb.poo.menu;

import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TestsGeneralTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void shouldGetProduct() {
        Product product = productRepository.findById(802L).orElse(null);
        assertNotNull(product, "Product should not be null");
        System.out.println(product); // Vai chamar toString sem carregar categorias
    }

    @Test
    public void shouldGetProductWithCategories() {
        Product product = productRepository.findByIdWithCategories(802L);
        assertNotNull(product, "Product should not be null");
        System.out.println(product); // Chama toString e carrega categorias
    }

    @Test
    public void shouldGetCategory() {
        Category category = categoryRepository.findById(2L).orElse(null); // USER ADMIN
        assertNotNull(category, "Category should not be null");
        System.out.println(category); // DEBUG
    }

    @Test
    public void shouldGetCategoryWithProductsByUser() {
        List<Category> categories = categoryRepository.getCategoriesWithProductsByUserId(302L);  // USER ADMIN
        assertFalse(categories.isEmpty(), "Category list should not be empty");

        // Exibe as categorias e seus produtos associados
        categories.forEach(category -> {
            System.out.println(category.toStringWithProducts());
        });
    }

    @Test
    public void shouldGetUser() {
        User user = userRepository.findById(857L).orElse(null); // USER ADMIN
        assertNotNull(user, "User should not be null");
        System.out.println(user); // DEBUG
    }

    @Test
    public void shouldGetClient() {
        Client client = clientRepository.findById(858L).orElse(null); // USER ADMIN
        assertNotNull(client, "Client should not be null");
        System.out.println(client); // DEBUG
    }

    @Test
    public void shouldGetClientsByUser() {
        List<Client> clients = clientRepository.findByUserId(302L); // USER ADMIN
        assertNotNull(clients, "Clients should not be null");
        System.out.println(clients); // DEBUG
    }

    @Test
    public void shouldGetUsersByUser() {
        List<User> users = userRepository.findByUserRegisterId(302L); // USER ADMIN
        assertNotNull(users, "Users should not be null");
        System.out.println(users); // DEBUG
    }

    @Test
    public void shouldGetProductsByUser() {
        List<Product> products = productRepository.findProductsByUserId(302L); // USER ADMIN
        assertNotNull(products, "Products should not be null");
        System.out.println(products); // DEBUG
    }

    @Test
    public void shouldGetCategoriesByUser() {
        List<Category> categories = categoryRepository.findByUserId(302L); // USER ADMIN
        assertNotNull(categories, "Categories should not be null");
        System.out.println(categories); // DEBUG
    }

    @Test
    public void shouldGetOrdersByUser() {
        List<Order> orders = orderRepository.findByUserId(302L); // USER ADMIN
        assertNotNull(orders, "Orders should not be null");
        System.out.println(orders); // DEBUG
    }

    @Test
    public void shouldGetCart() {
        Cart cart = cartRepository.findById(1L).orElse(null); // USER ADMIN
        System.out.println(cart);
        assertNotNull(cart.getId());
    }

    @Test
    public void shouldGetCartItem() {
        CartItem cartItem = cartItemRepository.findById(1L).orElse(null); // USER ADMIN
        System.out.println(cartItem);
        assertNotNull(cartItem.getId());
    }

    @Test
    public void shouldGetOrder() {
        Order order = orderRepository.findOrderWithItemsById(3L);
        assertNotNull(order, "Order should not be null");
         if(order != null) {
             System.out.println(order.toStringWithItems());
         }
    }
}