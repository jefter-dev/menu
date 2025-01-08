package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.OperatingHours;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.OperatingHoursRepository;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OperatingHoursRepository operatingHoursRepository;

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String userName) throws UserNotFoundException, InvalidFieldException {
        if (userName.isEmpty()) {
            throw new InvalidFieldException("Username vazio");
        }

        try {
            User user = userRepository.findByUsernameWithOperatingHours(userName);
            if(user == null) {
                throw new UserNotFoundException("Usuário não existe");
            }

            return user;
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    // Método para adicionar horários de funcionamento para um usuário
    public User addOperatingHours(User user, OperatingHours operatingHours) throws UserNotFoundException {
        // Verifica se o usuário existe
        User userFind = userRepository.findByIdWithOperatingHours(user.getId());

        if(userFind == null) {
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



}