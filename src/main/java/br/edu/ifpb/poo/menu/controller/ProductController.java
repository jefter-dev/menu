package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController implements BaseController<Product> {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<List<Product>> findAll() {
        System.out.println("[Controller] ProductController > findAll");
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        System.out.println("[Controller] ProductController > findById");

        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Product> create(@RequestBody Product product) {
        System.out.println("[Controller] ProductController > create");
        System.out.println(product);

        Product savedProduct = productService.create(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        System.out.println("[Controller] ProductController > update");

        product.setId(id);
        Product updatedProduct = productService.update(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        System.out.println("[Controller] ProductController > deleteById");

        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
