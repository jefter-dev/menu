package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.model.LoginRequest;
import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.model.Views;
import br.edu.ifpb.poo.menu.service.ClientService;
import br.edu.ifpb.poo.menu.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientService clientService;
    private final UserService userService;

    public AuthController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @JsonView(Views.SimpleView.class)
    @PostMapping(value = "/user/login", consumes = {"application/json", "multipart/form-data"})
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @JsonView(Views.SimpleView.class)
    @PostMapping(value = "/client/login", consumes = {"application/json", "multipart/form-data"})
    public ResponseEntity<?> loginClient(@RequestBody LoginRequest loginRequest) {
        try {
            Client client = clientService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
