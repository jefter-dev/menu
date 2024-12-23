package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldSaveOrder() {
        User userAdmin = userRepository.findById(302L).orElse(null); // USER ADMIN
        assertNotNull(userAdmin, "User should not be null");
        System.out.println(userAdmin); // DEBUG

        Client client = clientRepository.findById(52L).orElse(null); // USER ADMIN
        assertNotNull(client, "Client should not be null");
        System.out.println(client); // DEBUG

        Order newOrder = new Order(BigDecimal.valueOf(10), "Pending", client, userAdmin);
        System.out.println(newOrder);
        Order savedOrder = orderRepository.save(newOrder);

        System.out.println(savedOrder);

        Product product = productRepository.findById(802L).orElse(null);
        assertNotNull(product, "Product should not be null");
        System.out.println(product);

        OrderItem newOrderItem1 = new OrderItem(savedOrder, product, 1, BigDecimal.valueOf(7));
        OrderItem newOrderItem2 = new OrderItem(savedOrder, product, 2, BigDecimal.valueOf(3));

        OrderItem savedOrderItem1 = orderItemRepository.save(newOrderItem1);
        assertNotNull(savedOrderItem1.getId());

        OrderItem savedOrderItem2 = orderItemRepository.save(newOrderItem2);
        assertNotNull(savedOrderItem2.getId());

        System.out.println(savedOrderItem1);
        System.out.println(savedOrderItem2);
    }

    @Test
    public void getFindOrderById() {
        Order order = orderRepository.findById(1L).orElse(null);
        assertNotNull(order, "Order should not be null");
        System.out.println(order);
    }
}