package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.InvalidUserException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private CategoryService categoryService;

    public List<Category> getAllWithProductsByUser(User user) {
        try {
            List<Category> categories = categoryService.getAllWithProductsByUser(user);
            System.out.println(categories);

            return categories;
        } catch (InvalidUserException e) {
            System.out.println(e.getMessage());
            // throw new RuntimeException(e);
        }
        return List.of();
    }
}
