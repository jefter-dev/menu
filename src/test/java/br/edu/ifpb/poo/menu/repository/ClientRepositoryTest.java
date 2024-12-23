package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Address;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveClient() {
        User userAdmin = userRepository.findById(302L).orElse(null); // USER ADMIN
        assertNotNull(userAdmin, "User should not be null");
//        System.out.println(userAdmin); // DEBUG

        String newNameClient = "Jéfter Lucas";

        Client client = new Client(newNameClient, "client-test@menu.poo", userAdmin);
        Address address = new Address("Rua Cicero Galdino, Res Madrid", "58135-000", "CASA 12", "Esperança");
        client.setAddress(address);

        Client savedClient = clientRepository.save(client);

        assertNotNull(savedClient.getId());
        assertEquals(newNameClient, savedClient.getName(), "The client name should match the expected value");
    }

    @Test
    public void shouldUpdateClient() {
        // Buscar um cliente existente para atualização
        Client clientToUpdate = clientRepository.findById(2L).orElse(null); // Supondo que o cliente com ID 1 exista no banco
        assertNotNull(clientToUpdate, "Client should not be null");
//        System.out.println(clientToUpdate); // DEBUG

        // Alterar o nome e e-mail do cliente
        String updatedName = "Jéfter Lucas Updated";
        String updatedEmail = "jefter.updated@menu.poo";
        clientToUpdate.setName(updatedName);
        clientToUpdate.setEmail(updatedEmail);

        // Salvar o cliente atualizado
        Client updatedClient = clientRepository.save(clientToUpdate);

        // Verificar se as alterações foram aplicadas corretamente
        assertEquals(updatedName, updatedClient.getName(), "The client name should be updated");
        assertEquals(updatedEmail, updatedClient.getEmail(), "The client email should be updated");

        // Opcionalmente, verificar diretamente no banco de dados (se necessário)
        Client foundClient = clientRepository.findById(updatedClient.getId()).orElse(null);
        assertNotNull(foundClient, "The updated client should still exist in the database");
        assertEquals(updatedName, foundClient.getName(), "The updated client name should be in the database");
        assertEquals(updatedEmail, foundClient.getEmail(), "The updated client email should be in the database");
    }

    @Test
    public void shouldDeleteClient() {
        // Criar um novo cliente para testar a deleção
        User userAdmin = userRepository.findById(302L).orElse(null); // USER ADMIN
        assertNotNull(userAdmin, "User should not be null");

        Client client = new Client("Cliente para Deletar", "cliente.delete@menu.poo", userAdmin);
        Client savedClient = clientRepository.save(client);

        // Verificar se o cliente foi salvo corretamente
        assertNotNull(savedClient.getId(), "The client ID should not be null before deletion");

        // Deletar o cliente
        clientRepository.delete(savedClient);

        // Verificar se o cliente foi removido
        Client deletedClient = clientRepository.findById(savedClient.getId()).orElse(null);
        assertNull(deletedClient, "The client should be null after deletion");
    }

    @Test
    public void shouldListClientsByUser() {
        // Buscar todos os clientes associados ao usuário
        List<Client> clientsByUser = clientRepository.findByUserId(302L);

        // Verificar se a lista de clientes não está vazia
        assertNotNull(clientsByUser, "The list of clients should not be null");
        assertFalse(clientsByUser.isEmpty(), "The list of clients should not be empty");
    }

    @Test
    public void shouldGetCategory() {
        Client client = clientRepository.findById(52L).orElse(null); // USER ADMIN
        assertNotNull(client, "Client should not be null");
        System.out.println(client); // DEBUG
    }
}