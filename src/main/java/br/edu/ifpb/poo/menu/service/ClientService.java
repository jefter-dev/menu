package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    private void validateUserFields(Client client) throws InvalidFieldException {
        validateField(client.getName(), "O nome não pode estar vazio.");
        validateField(client.getEmail(), "O email não pode estar vazio.");
    }

    private void validateField(String field, String errorMessage) throws InvalidFieldException {
        if (field == null || field.isEmpty()) {
            throw new InvalidFieldException(errorMessage);
        }
    }

    // Método para criar um cliente
    public Client createClient(Client client) throws ClientNotFoundException, InvalidFieldException {
        validateUserFields(client);

        // Só valida a senha se ela for fornecida (não nula ou vazia)
        if (client.getPassword() != null && client.getPassword().isEmpty()) {
            throw new InvalidFieldException("A senha não pode estar vazia.");
        }

        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    // Método para obter um cliente por ID
    public Client getClientById(Long clientId) throws ClientNotFoundException {
        if (clientId == null) {
            throw new ClientNotFoundException("Cliente inválido.");
        }

        // Busca o cliente pelo ID do cliente e ID do usuário
        return clientRepository.findById(clientId).orElse(null);
    }

    // Método para obter um cliente por ID
    public Client getClientByIdAndUser(Long clientId, User user) throws ClientNotFoundException {
        // Verifica se o usuário ou IDs são nulos
        if (user == null || user.getId() == null || clientId == null) {
            throw new ClientNotFoundException("Usuário ou cliente inválido.");
        }

        // Busca o cliente pelo ID do cliente e ID do usuário
        return clientRepository.findByIdAndUserId(clientId, user.getId());
    }

    public Client findByIdAndUserId(Long clientId, User user) throws ClientNotFoundException {
        // Verifica se o usuário ou IDs são nulos
        if (user == null || user.getId() == null || clientId == null) {
            throw new ClientNotFoundException("Usuário ou cliente inválido.");
        }

        // Busca o cliente pelo ID do cliente e ID do usuário
        return clientRepository.findByIdAndUserId(clientId, user.getId());
    }

    // Método para atualizar um cliente
    public Client updateClient(Long id, Client updatedClient) throws ClientNotFoundException {
        if (updatedClient == null) {
            throw new IllegalArgumentException("Os dados do cliente atualizado não podem ser nulos.");
        }

        // Verifica se o cliente existe no banco de dados
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente com ID " + id + " não encontrado."));

        // Atualiza apenas os campos permitidos
        if (updatedClient.getName() != null && !updatedClient.getName().isEmpty()) {
            existingClient.setName(updatedClient.getName());
        }

        if (updatedClient.getEmail() != null && !updatedClient.getEmail().isEmpty()) {
            existingClient.setEmail(updatedClient.getEmail());
        }

        // Atualiza o endereço, se necessário
        if (updatedClient.getAddress() != null) {
            existingClient.setAddress(updatedClient.getAddress());
        }

        // Atualiza o usuário associado, se necessário
        if (updatedClient.getUser() != null) {
            existingClient.setUser(updatedClient.getUser());
        }

        try {
            // Salva as alterações no banco de dados
            return clientRepository.save(existingClient);
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
