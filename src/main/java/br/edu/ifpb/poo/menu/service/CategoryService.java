package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.InvalidUserException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CrudService {
    @Autowired
    private CategoryRepository categoryRepository;

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

    public List<Category> getAllWithProductsByUser(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("O usuário fornecido fornecido não encontrado.");
        }

        return categoryRepository.findAllWithProductsByUserId(user.getId());
    }
}
