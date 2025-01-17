package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.cart.CartNotFoundException;
import br.edu.ifpb.poo.menu.exception.order.InvalidStatusOrderException;
import br.edu.ifpb.poo.menu.exception.order.OrderNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.service.OrderService;
import br.edu.ifpb.poo.menu.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @JsonView(Views.SimpleView.class)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody String status
    ) throws OrderNotFoundException, InvalidStatusOrderException, UserNotFoundException {
//        System.out.println("ATUALIZANDO STATUS: " + status);
        Order orderFind = orderService.getOrderById(orderId);
        if (orderFind == null) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderService.updateOrderStatus(orderFind, status);

        return ResponseEntity.ok(order);
    }

    @JsonView(Views.SimpleView.class)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws InvalidFieldException {
        Order newOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);  // Retorna 201 Created com o produto salvo
    }

    @JsonView(Views.SimpleView.class)
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long orderId,
            @RequestBody Order updatedOrder
    ) throws OrderNotFoundException, InvalidFieldException {
        Order orderUpdated = orderService.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(orderUpdated);
    }

    @JsonView(Views.SimpleView.class)
    @PostMapping("/from-cart")
    public ResponseEntity<Order> createOrderWithCart(
            @RequestBody Cart cart,
            @RequestParam Long userId
    ) throws UserNotFoundException, CartNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        User user = new User();
        user.setId(userId);
        Order newOrder = orderService.createOrderWithCart(cart, user);
        return ResponseEntity.ok(newOrder);
    }

    @JsonView(Views.SimpleView.class)
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content quando a exclusão é bem-sucedida
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o pedido não existir
        }
    }

}