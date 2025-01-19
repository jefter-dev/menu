package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.Views;
import br.edu.ifpb.poo.menu.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/product")
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
    @JsonView(Views.SimpleView.class)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) throws InvalidFieldException {
        // Cria o produto e retorna 201 Created
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);  // Retorna 201 Created com o produto salvo
    }

    @Override
    @JsonView(Views.SimpleView.class)
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) throws ProductNotFoundException, InvalidFieldException, AdditionalNotFoundException {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();  // Retorna 204 No Content
    }

    /**
     * Rota para fazer upload de uma imagem associada a um produto.
     */
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file
    ) throws ProductNotFoundException, InvalidFieldException {
        // Chama o serviço para salvar a imagem
        productService.uploadProductImage(id, file);
        return ResponseEntity.ok("Imagem enviada com sucesso.");
    }

}


