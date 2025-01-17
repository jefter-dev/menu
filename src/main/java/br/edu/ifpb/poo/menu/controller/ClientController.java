package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.cart.CartNotFoundException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.exception.order.OrderNotFoundException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public ClientController(ClientService clientService, CartService cartService, OrderService orderService) {
        this.clientService = clientService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientByUser(@PathVariable Long id) throws ClientNotFoundException {
        Client clientFind = clientService.getClientById(id);
        if (clientFind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientFind);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{id}/cart")
    public ResponseEntity<Cart> getCartByClient(@PathVariable Long id) throws ClientNotFoundException, CartNotFoundException {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = cartService.findCartByClient(client);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getOrdersByClient(@PathVariable Long id) throws ClientNotFoundException, OrderNotFoundException {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        List<Order> orders = orderService.findOrdersByClientId(client); // Presume-se que Client tenha o método getOrders()
        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }
}
