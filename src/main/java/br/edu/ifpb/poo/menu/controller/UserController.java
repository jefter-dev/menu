package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ClientService clientService;

    @Autowired
    public UserController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    // Painel ADMIM
    @JsonView(Views.SimpleView.class)
    @GetMapping("/{userId}/users")
    public ResponseEntity<List<User>> getUsersDetails(@PathVariable Long userId) throws UserNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<User> users = userService.getUsersByUserRegister(userFind);

        return ResponseEntity.ok(users);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{userId}/clients")
    public ResponseEntity<List<Client>> getClients(@PathVariable Long userId) throws UserNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<Client> clients = userService.getClientsByUser(userFind);

        return ResponseEntity.ok(clients);
    }

    @JsonView(Views.ProductView.class)
    @GetMapping("/{userId}/products")
    public ResponseEntity<List<Product>> getProducts(@PathVariable Long userId) throws UserNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<Product> products = userService.getProductsByUser(userFind);

        return ResponseEntity.ok(products);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{userId}/additionals")
    public ResponseEntity<List<Additional>> getAdditionals(@PathVariable Long userId) throws UserNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<Additional> addicionals = userService.getAdditionalsByUser(userFind);

        return ResponseEntity.ok(addicionals);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{userId}/categories")
    public ResponseEntity<List<Category>> getCategories(@PathVariable Long userId) throws UserNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<Category> categories = userService.getCategoriesByUser(userFind);

        return ResponseEntity.ok(categories);
    }

    @JsonView(Views.SimpleView.class)
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable Long userId, @RequestParam(required = false) String status) throws UserNotFoundException {
        User userFind = userService.getUserById(userId);
        if (userFind == null) {
            return ResponseEntity.notFound().build();
        }

        List<Order> orders = userService.getOrdersByUserFilterStatus(userFind, status);

        return ResponseEntity.ok(orders);
    }

    // Novo endpoint para obter usuários com filtro de admin
    @JsonView(Views.DetailedView.class)
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) Boolean admin) {
        List<User> users = userService.getAllUsersFilterAdmin(admin);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @JsonView(Views.SimpleView.class)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) throws InvalidFieldException, InvalidUserException {
        User createdUser = userService.createUser(user); // Método no UserService
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @JsonView(Views.SimpleView.class)
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody User updatedUser
    ) throws UserNotFoundException, InvalidFieldException {
        User updatedUserResult = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(updatedUserResult);
    }

    @JsonView(Views.SimpleView.class)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        userService.deleteUser(userId); // Método no UserService para deletar o usuário
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload/image")
    public ResponseEntity<String> uploadUserImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file
    ) throws UserNotFoundException, InvalidFieldException  {
        // Chama o serviço para salvar a imagem
        userService.uploadUserImage(id, file, "image");
        return ResponseEntity.ok("Imagem enviada com sucesso.");
    }

    @PostMapping("/{id}/upload/banner")
    public ResponseEntity<String> uploadUserBanner(
            @PathVariable Long id,
            @RequestParam("banner") MultipartFile file
    ) throws UserNotFoundException, InvalidFieldException  {
        // Chama o serviço para salvar o banner
        userService.uploadUserImage(id, file, "banner");
        return ResponseEntity.ok("Banner enviado com sucesso.");
    }
}
