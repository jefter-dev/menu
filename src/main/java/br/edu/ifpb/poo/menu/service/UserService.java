package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) throws InvalidFieldException {
        // Valida os campos do usuário
        validateUserFields(user);

        // Valida e criptografa a senha, se fornecida
        if (user.getPassword() != null && user.getPassword().isEmpty()) {
            throw new InvalidFieldException("A senha não pode estar vazia.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            // Salva o usuário no banco de dados (ID gerado aqui)
            User savedUser = userRepository.save(user);

            // Persistindo as horas de funcionamento separadamente para garantir a transação
            if (user.getOperatingHours() != null) {
                for (OperatingHours operatingHour : user.getOperatingHours()) {
                    operatingHour.setUser(savedUser); // Associando corretamente antes de salvar
                }
                // Salva os horários de funcionamento manualmente, para garantir persistência
                savedUser.setOperatingHours(user.getOperatingHours()); // Persistindo as horas de funcionamento
            }

            // Retorna o usuário com os horários de funcionamento já associados
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    private void validateUserFields(User user) throws InvalidFieldException {
        validateField(user.getName(), "O nome não pode estar vazio.");
        validateField(user.getUsername(), "O username não pode estar vazio.");
        validateField(user.getEmail(), "O email não pode estar vazio.");
    }

    private void validateField(String field, String errorMessage) throws InvalidFieldException {
        if (field == null || field.isEmpty()) {
            throw new InvalidFieldException(errorMessage);
        }
    }

    // Método de login por email e senha
    public User login(String email, String password) throws UserNotFoundException, InvalidFieldException {
        if (email == null || email.isEmpty()) {
            throw new InvalidFieldException("O email não pode estar vazio.");
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidFieldException("A senha não pode estar vazia.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Usuário com este email não foi encontrado.");
        }

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidFieldException("Credenciais inválidas.");
        }

        return user;
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllWithDetails();
    }

    public List<User> getAllUsersFilterAdmin(Boolean admin) {
        if (admin != null) {
            return userRepository.findAllWithDetailsByAdmin(admin); // Filtra com base no atributo admin
        }
        return this.getAllUsers(); // Retorna todos os usuários caso admin seja null
    }

    public List<Client> getClientsByUser(User user) {
        try {
            return userRepository.findClientsByUserId(user.getId()); // Filtra com base no atributo admin
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public List<Product> getProductsByUser(User user) {
        try {
            return userRepository.getProductsByUserIdWithAdditionals(user.getId()); // Filtra com base no atributo admin
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public List<Additional> getAdditionalsByUser(User user) {
        try {
            return userRepository.getAdditionalsByUserId(user.getId()); // Filtra com base no atributo admin
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public List<Category> getCategoriesByUser(User user) {
        try {
            return userRepository.getCatgegoriesByUserId(user.getId()); // Filtra com base no atributo admin
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public List<Order> getOrdersByUserFilterStatus(User user, String status) {
        try {
            if (status != null) {
                return userRepository.findOrdersByUserIdAndStatusWithDetails(user.getId(), status); // Filtra com base no atributo admin
            }
            return userRepository.findOrdersByUserIdWithDetails(user.getId()); // Filtra com base no atributo admin
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public User findByUsername(String userName) throws UserNotFoundException, InvalidFieldException {
        if (userName.isEmpty()) {
            throw new InvalidFieldException("Username vazio");
        }

        try {
            User user = userRepository.findByUsernameWithOperatingHours(userName);
            if (user == null) {
                throw new UserNotFoundException("Usuário não existe");
            }

            return user;
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public List<User> getUsersByUserRegister(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        try {
            List<User> users = userRepository.findAllByUserRegisterId(user.getId());
            if (users == null) {
                throw new UserNotFoundException("Usuário não existe");
            }

            return users;
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    // Método para adicionar horários de funcionamento para um usuário
    public User addOperatingHours(User user, OperatingHours operatingHours) throws UserNotFoundException {
        // Verifica se o usuário existe
        User userFind = userRepository.findByIdWithOperatingHours(user.getId());

        if (userFind == null) {
            throw new UserNotFoundException("Usuário não existe");
        }

        // Associa os horários ao usuário
        userFind.addOperatingHour(operatingHours);
        return userRepository.save(userFind);
    }

    public User updateOperatingHours(User user, List<OperatingHours> newOperatingHours) throws UserNotFoundException {
        // Verifica se o usuário existe no banco de dados
        User userFind = userRepository.findByIdWithOperatingHours(user.getId());

        if (userFind == null) {
            throw new UserNotFoundException("Usuário não existe");
        }

        // Remove os horários antigos
        List<OperatingHours> existingOperatingHours = new ArrayList<>(userFind.getOperatingHours());
        for (OperatingHours oh : existingOperatingHours) {
            userFind.removeOperatingHour(oh);
        }

        // Adiciona os novos horários
        for (OperatingHours oh : newOperatingHours) {
            userFind.addOperatingHour(oh);
        }

        // Salva as alterações no repositório
        return userRepository.save(userFind);
    }

    public User updateUser(Long userId, User updatedUser) throws UserNotFoundException, InvalidFieldException {
        // Valida os campos do usuário
        validateUserFields(updatedUser);

        // Verifica se o usuário existe no banco de dados
        User existingUser = userRepository.findByIdWithOperatingHours(userId);
        if (existingUser == null) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        // Atualiza os dados básicos do usuário
        existingUser.setName(updatedUser.getName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());

        // Atualiza a senha somente se for informada
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Atualiza os campos adicionais
        existingUser.setAdmin(updatedUser.isAdmin());
        existingUser.setCnpj(updatedUser.getCnpj());
        existingUser.setSocialReason(updatedUser.getSocialReason());

        // Atualiza o endereço
        if (updatedUser.getAddress() != null) {
            Address updatedAddress = updatedUser.getAddress();
            existingUser.setAddress(updatedAddress);
        }

        // Atualiza os horários de funcionamento
        if (updatedUser.getOperatingHours() != null) {
            // Remove os horários antigos
            existingUser.getOperatingHours().clear();

            // Associa os novos horários ao usuário
            for (OperatingHours operatingHour : updatedUser.getOperatingHours()) {
                existingUser.addOperatingHour(operatingHour);
            }
        }

        System.out.println("existingUser: " + existingUser.toStringDetails());

        try {
            // Salva as alterações no banco de dados
            return userRepository.save(existingUser);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public void deleteUser(Long userId) throws UserNotFoundException {
        try {
            User user = userRepository.findByIdWithOperatingHours(userId);
            if (user == null) {
                throw new UserNotFoundException("Usuário não encontrado");
            }
            userRepository.delete(user);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public void uploadUserImage(Long id, MultipartFile file) throws UserNotFoundException, InvalidFieldException {
        System.out.println("uploadUserImage");

        // Verifica se o produto existe
        User user = getUserById(id);

        // Valida o arquivo
        if (file == null || file.isEmpty()) {
            throw new InvalidFieldException("O arquivo de imagem não pode estar vazio.");
        }

        try {
            // Alternativa usando um caminho absoluto, por exemplo no diretório home do usuário
            Path uploadDirectory = Paths.get(System.getProperty("user.dir"), "uploads", "user");

            // Verifica se o diretório existe, se não cria
            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }

            // Obtém o nome do arquivo original
            String imageName = file.getOriginalFilename();

            // Define o caminho completo para armazenar o arquivo
            assert imageName != null;
            Path filePath = uploadDirectory.resolve(imageName);

            // Salva o arquivo fisicamente no diretório
            file.transferTo(filePath.toFile());

            // Atualiza o produto com o nome do arquivo
            user.setImage(imageName);

            // Salva as alterações no usuário
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem. Detalhes: " + e.getMessage(), e);
        }
    }

}