package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    // Método para criar um cliente
    public Client createClient(Client client) throws ClientNotFoundException {
        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    // Método para obter um cliente por ID
    public Client getClientById(Long id) throws ClientNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }
        return clientOptional.get();
    }

    // Método para atualizar um cliente
    public Client updateClient(Long id, Client updatedClient) throws ClientNotFoundException {
        if (updatedClient == null) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        // Verifica se o cliente existe
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        // Atualiza os dados do cliente
        Client client = clientOptional.get();
        client.setName(updatedClient.getName()); // Supondo que a classe Client tenha um nome
        client.setEmail(updatedClient.getEmail()); // E-mail como exemplo de outro campo

        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    // Método para excluir um cliente
    public void deleteClient(Long id) throws ClientNotFoundException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }
        clientRepository.deleteById(id);
    }
}
