package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Test
    void createClient() {
        try {
            User user = new User();
            user.setId(302L);

            Client client = new Client("NOVO CLIENTE TESTE 2", "novoclienteteste@email.com", user);

            Client newClient = clientService.createClient(client);
            System.out.println(newClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void updateClient() {
        try {
            User user = new User();
            user.setId(302L);

            Client client = new Client("ATUALIZAR CLIENTE", "clienteAtualizado@email.com", user);

            Client newClient = clientService.updateClient(864L, client);
            System.out.println(newClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findByIdAndUserId() {
        try {
            User user = new User();
            user.setId(302L);

            Client newClient = clientService.findByIdAndUserId(864L, user);
            System.out.println(newClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getClientById() {
        try {
            Client newClient = clientService.getClientById(858L);
            System.out.println(newClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}