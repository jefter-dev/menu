package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
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
@RequestMapping("/{user}")
public class UserController {
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductAdditionalService productAdditionalService;
    private final ClientService clientService;

    @Autowired
    public UserController(ProductService productService, UserService userService,
                          CategoryService categoryService, ProductAdditionalService productAdditionalService, ClientService clientService) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productAdditionalService = productAdditionalService;
        this.clientService = clientService;
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsByUser(@PathVariable String user) throws UserNotFoundException, InvalidFieldException, InvalidUserException {
        User userFind = userService.findByUsername(user);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        List<Product> products = productService.getProductsByUser(userFind);
        return ResponseEntity.ok(products);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategoriesWithProductsByUser(@PathVariable String user) throws UserNotFoundException, InvalidFieldException, InvalidUserException {
        User userFind = userService.findByUsername(user);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        List<Category> categories = categoryService.getCategoriesWithProductsByUserId(userFind);
        return ResponseEntity.ok(categories);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping
    public ResponseEntity<User> getUserDetails(@PathVariable String user) throws UserNotFoundException, InvalidFieldException {
        User userFind = userService.findByUsername(user);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userFind);
    }

    // Novo endpoint para buscar adicionais de um produto
    @JsonView(Views.SimpleView.class)
    @GetMapping("/products/{productId}/additionals")
    public ResponseEntity<List<Additional>> getAdditionalsByProduct(@PathVariable String user, @PathVariable Long productId) throws UserNotFoundException, InvalidFieldException, ProductNotFoundException {
        User userFind = userService.findByUsername(user);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        List<Additional> additionals = productAdditionalService.getAdditionalsByProductId(product);
        return ResponseEntity.ok(additionals);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Client> getClientByUser(@PathVariable String user, @PathVariable Long clientId) {
        try {
            // Busca o usuário pelo nome
            User userFind = userService.findByUsername(user);
            if (userFind == null) {
                return ResponseEntity.notFound().build();
            }

            // Busca o cliente associado ao usuário e ID do cliente
            Client client = clientService.getClientByIdAndUser(clientId, userFind);
            return ResponseEntity.ok(client);

        } catch (UserNotFoundException | ClientNotFoundException | InvalidFieldException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
