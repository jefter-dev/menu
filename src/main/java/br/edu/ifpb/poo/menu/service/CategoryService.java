package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.category.CategoryNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /**
     * Retorna uma categoria pelo ID.
     */
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        try {
            Optional<Category> categoryFind = categoryRepository.findById(id);
            if (categoryFind.isEmpty()) {
                throw new CategoryNotFoundException("Categoria não encontrada com o ID fornecido.");
            }
            return categoryFind.get();
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Retorna categorias vinculadas a um usuário.
     */
    public List<Category> getCategoriesWithProductsByUser(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("Usuário fornecido não encontrado.");
        }
        try {
            return categoryRepository.getCategoriesWithProductsByUserId(user.getId());
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Cria uma nova categoria.
     */
    public Category createCategory(Category category) throws InvalidFieldException {
        validateCategory(category);
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Atualiza uma categoria existente.
     */
    public Category updateCategory(Long id, Category updatedCategory) throws CategoryNotFoundException, InvalidFieldException {
        Category existingCategory = getCategoryById(id);
        validateCategory(updatedCategory);

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setUpdatedAt(updatedCategory.getUpdatedAt());

        try {
            return categoryRepository.save(existingCategory);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Deleta uma categoria pelo ID.
     */
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        Category category = getCategoryById(id);
        try {
            categoryRepository.delete(category);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Valida os campos da categoria.
     */
    private void validateCategory(Category category) throws InvalidFieldException {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new InvalidFieldException("O nome da categoria não pode ser nulo ou vazio.");
        }
    }
}