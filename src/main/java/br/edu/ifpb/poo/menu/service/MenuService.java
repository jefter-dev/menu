package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public List<Category> getCategoriesWithProductsForUser(User user) throws InvalidUserException {
        return categoryService.getAllWithProductsByUser(user);
    }

    public List<Product> findProduct(String name, User user) throws InvalidUserException, ProductNotFoundException {
        return productService.searchByNameForUser(name, user);
    }

    public User getUserDetails(User user) throws UserNotFoundException {
        return userService.getUserById(user.getId());
    }
}
