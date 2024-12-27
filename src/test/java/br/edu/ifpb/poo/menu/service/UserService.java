package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exceptions.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) throws UserNotFoundException, InvalidUserException {
        if (id == null) {
            throw new InvalidUserException("O usuário fornecido não encontrado.");
        }

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
