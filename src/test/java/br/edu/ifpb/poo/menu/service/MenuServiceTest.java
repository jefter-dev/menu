package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MenuServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllWithProductsByUser() {
        User userTest = userRepository.findById(302L).orElse(null); // USER ADMIN
        System.out.println("USUARIO TESTE " + userTest);

        User user = userService.getUserById(302L); // USER ADMIN
        System.out.println("USUARIO DE SERVICE" + user);
//            try {
//                User userTest = userRepository.findById(302L).orElse(null); // USER ADMIN
//                System.out.println("USUARIO TESTE "+ userTest);
//
//                User user = userService.getUserById(302L); // USER ADMIN
//                System.out.println("USUARIO DE SERVICE"+user);
//            } catch (UserNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
    }
}