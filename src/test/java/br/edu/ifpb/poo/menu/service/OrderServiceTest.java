package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    void createOrder() {
        try {
            Cart cart = new Cart();
            cart.setId(14L);

            User user = new User();
            user.setId(302L);

            orderService.createOrderWithCart(cart, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findOrderWithDetails() {
        try {
            Order orderFind = new Order();
            orderFind.setId(19L);

            Order order = orderService.findOrderWithDetails(orderFind);
            if (order != null) {
                System.out.println("ORDER: " + order.toStringWithItems());
            }
        } catch (Exception e) {
            System.out.println("ERROR [findOrderWithDetails]: " + e.getMessage());
        }
    }

    @Test
    void findOrdersByClientId() {
        try {
            Client client = new Client();
            client.setId(2L);

            List<Order> orders = orderService.findOrdersByClientId(client);
            if (orders != null) {
                for (int i = 0; i < orders.size(); i++) {
                    Order order = orders.get(i);

                    System.out.println("ORDER " + i + ": " + order.toStringWithItems());
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR [findOrderWithDetails]: " + e.getMessage());
        }
    }

    @Test
    void updateOrderStatus() {
        try {
            Order orderFind = new Order();
            orderFind.setId(18L);

            Order orderUpdated = orderService.updateOrderStatus(orderFind, OrderStatus.PROCESSING.getStatus());
            System.out.println(orderUpdated);
        } catch (Exception e) {
            System.out.println("ERROR [findOrderWithDetails]: " + e);
        }
    }
}