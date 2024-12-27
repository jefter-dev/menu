package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElse(null);
//        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
//        System.out.println("USER DENTRO DE SERVICE: "+user);
//        return user;
    }

    @Override
    public Object create(Object entity) {
        return null;
    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}