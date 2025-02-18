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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{establishment}")
public class MenuController {
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductAdditionalService productAdditionalService;
    private final ClientService clientService;

    @Autowired
    public MenuController(ProductService productService, UserService userService,
                          CategoryService categoryService, ProductAdditionalService productAdditionalService, ClientService clientService) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productAdditionalService = productAdditionalService;
        this.clientService = clientService;
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsByUser(@PathVariable String establishment) throws UserNotFoundException, InvalidFieldException, InvalidUserException {
        User userFind = userService.findByUsername(establishment);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        List<Product> products = productService.getProductsByUser(userFind);
        return ResponseEntity.ok(products);
    }

    @JsonView(Views.CategoryView.class)
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategoriesWithProductsByUser(@PathVariable String establishment) throws UserNotFoundException, InvalidFieldException, InvalidUserException {
        User userFind = userService.findByUsername(establishment);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        List<Category> categories = categoryService.getCategoriesWithProductsByUserId(userFind);
        return ResponseEntity.ok(categories);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/")
    public ResponseEntity<User> getUserDetails(@PathVariable String establishment) throws UserNotFoundException, InvalidFieldException {
        User userFind = userService.findByUsername(establishment);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userFind);
    }

    // Novo endpoint para buscar adicionais de um produto
    @JsonView(Views.SimpleView.class)
    @GetMapping("/products/{productId}/additionals")
    public ResponseEntity<List<Additional>> getAdditionalsByProduct(@PathVariable String establishment, @PathVariable Long productId) throws UserNotFoundException, InvalidFieldException, ProductNotFoundException {
        User userFind = userService.findByUsername(establishment);
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
    public ResponseEntity<Client> getClientByUser(@PathVariable String establishment, @PathVariable Long clientId) {
        try {
            // Busca o usuário pelo nome
            User userFind = userService.findByUsername(establishment);
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EXCEPTION [register]: " + e.getMessage());
        }
    }

    // Painel ADMIM
    @JsonView(Views.SimpleView.class)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersDetails(@PathVariable Long userId) throws UserNotFoundException, InvalidFieldException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<User> users = userService.getUsersByUserRegister(userFind);

        return ResponseEntity.ok(users);
    }

    // *** NOVO ENDPOINT DE LOGIN ***
    @JsonView(Views.SimpleView.class)
    @PostMapping("/client/login")
    public ResponseEntity<?> loginClient(@PathVariable String establishment, @RequestBody LoginRequest loginRequest) {
        try {
            Client client = clientService.login(loginRequest.getEmail(), loginRequest.getPassword(), establishment);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
