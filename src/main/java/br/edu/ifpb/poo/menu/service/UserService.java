package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElse(null);
    }
}