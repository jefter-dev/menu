package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.category.CategoryNotFoundException;
import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.Views;
import br.edu.ifpb.poo.menu.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Retorna uma categoria pelo ID.
     */
    @JsonView(Views.SimpleView.class)
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) throws CategoryNotFoundException {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category); // Retorna 200 OK com a categoria
    }

    /**
     * Cria uma nova categoria.
     */
    @JsonView(Views.SimpleView.class)
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) throws InvalidFieldException {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    /**
     * Atualiza uma categoria existente.
     */
    @JsonView(Views.SimpleView.class)
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody Category updatedCategory
    ) throws CategoryNotFoundException, InvalidFieldException {
        Category updatedCategoryResult = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(updatedCategoryResult);
    }

    /**
     * Deleta uma categoria pelo ID.
     */
    @JsonView(Views.SimpleView.class)
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}