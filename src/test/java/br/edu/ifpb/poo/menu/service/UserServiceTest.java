package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.configuration.SecurityConfig;
import br.edu.ifpb.poo.menu.model.OperatingHours;
import br.edu.ifpb.poo.menu.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void findByUsername() {
        try {
            User userFind = userService.findByUsername("teste");
            System.out.println(userFind);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addOperatingHours() {
        try {
            User user = new User();
            user.setId(302l);

            // Criando o objeto OperatingHours com valores válidos
            OperatingHours operatingHours = new OperatingHours(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));
            operatingHours.setDayOfWeek(DayOfWeek.MONDAY); // Exemplo: Segunda-feira
            operatingHours.setOpeningTime(LocalTime.of(9, 0)); // Exemplo: 09:00
            operatingHours.setClosingTime(LocalTime.of(18, 0)); // Exemplo: 18:00

            // Chamando o método do serviço para adicionar horas de operação
            User userFind = userService.addOperatingHours(user, operatingHours);
            System.out.println(userFind.toStringDetails());
        } catch (Exception e) {
            // Exibindo a mensagem de erro caso ocorra uma exceção
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    @Test
    void updateOperatingHours() {
        try {
            // Criando o usuário de exemplo com ID
            User user = new User();
            user.setId(302L);

            // Criando uma lista de horários de funcionamento para atualizar
            List<OperatingHours> newOperatingHours = Arrays.asList(
                    new OperatingHours(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)), // Segunda-feira
                    new OperatingHours(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(17, 0)), // Terça-feira
                    new OperatingHours(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(17, 0)) // Quarta-feira
            );

            // Chamando o método do serviço para atualizar os horários de funcionamento
            User userFind = userService.updateOperatingHours(user, newOperatingHours);
            userFind.setCnpj("67712861000173");
            userFind.setSocialReason("Razão social teste");

            // Exibindo os detalhes do usuário atualizado para validação
            System.out.println(userFind.toStringDetails());
        } catch (Exception e) {
            // Exibindo a mensagem de erro caso ocorra uma exceção
            System.out.println(e.getMessage());
        }
    }

    @Test
    void register() {
        try {
            User user = new User();
            user.setName("TESTE CREATE");
            user.setEmail("contato@jefterdev.com");
            user.setPassword("desenv123");
            user.setUsername("jefterdev1");
//            user.setAdmin(false);

            User userCreated = userService.createUser(user);

            System.out.println("userCreated: " + userCreated);
        } catch (Exception e) {
            System.out.println("EXCEPTION [register]: " + e.getMessage());
        }
    }

    @Test
    void login() {
        try {
            User user = userService.login("contato@jefterdev.com", "desenv1231");
            System.out.println("LOGIN: " + user);
        } catch (Exception e) {
            System.out.println("EXCEPTION [login]: " + e.getMessage());
        }
    }

    @Test
    void getUsersByUserRegister() {
        try {
            User userRegister = new User();
            userRegister.setId(302L);

            List<User> users = userService.getUsersByUserRegister(userRegister);
            System.out.println("users: " + users);
        } catch (Exception e) {
            System.out.println("EXCEPTION [login]: " + e.getMessage());
        }
    }

    @Test
    void updateUser() {
        try {
            User user = new User();
            user.setId(1L);
            user.setName("USER TEST ATUALIZADO");
            user.setUsername("teste_update 123");

            User userUpdated = userService.updateUser(1L, user);

            System.out.println("userUpdated: " + userUpdated);
        } catch (Exception e) {
            System.out.println("EXCEPTION [register]: " + e.getMessage());
        }
    }
}
