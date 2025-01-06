package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.Views;
import br.edu.ifpb.poo.menu.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController implements BaseController<Product> {
    @Autowired
    private ProductService productService;

    @Override
    @JsonView(Views.SimpleView.class)
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);  // Retorna 200 OK com o produto
    }

    @Override
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        // Cria o produto e retorna 201 Created
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);  // Retorna 201 Created com o produto salvo
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            // Atualiza o produto e retorna 200 OK com o produto atualizado
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductNotFoundException e) {
            // Retorna 404 Not Found se o produto não for encontrado
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            // Deleta o produto e retorna 204 No Content
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content
        } catch (ProductNotFoundException e) {
            // Retorna 404 Not Found se o produto não for encontrado
            return ResponseEntity.notFound().build();
        }
    }
}