package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.OrderItem;
import br.edu.ifpb.poo.menu.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void getFindOrderItemById() {
        OrderItem orderItem = orderItemRepository.findById(1L).orElse(null);
        assertNotNull(orderItem, "Order item should not be null");
        System.out.println(orderItem);
    }
}