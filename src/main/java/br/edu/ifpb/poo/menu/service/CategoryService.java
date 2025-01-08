package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategoriesWithProductsByUserId(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("Usuário fornecido não encontrado.");
        }

        return categoryRepository.getCategoriesWithProductsByUserId(user.getId());
    }
}
