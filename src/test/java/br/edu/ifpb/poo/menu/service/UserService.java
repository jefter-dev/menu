package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        
        return userRepository.getReferenceById(id);
    }
}
