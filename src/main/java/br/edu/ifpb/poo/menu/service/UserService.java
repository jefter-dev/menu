package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    public User getUserById(Long id) throws UserNotFoundException {
//        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
//    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(302L).orElse(null);
        // Usando o método com join fetch
//        return userRepository.findByIdWithAssociations(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}