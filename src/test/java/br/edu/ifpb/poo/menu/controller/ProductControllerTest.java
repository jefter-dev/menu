package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
//        List<Product> products = List.of(new Product("Product 1", 100.0), new Product("Product 2", 150.0));
//        when(productService.findAll()).thenReturn(products);
//
//        ResponseEntity<List<Product>> response = productController.findAll();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(products, response.getBody());
    }

    @Test
    void testFindById() {
//        Product product = new Product("Product 1", 100.0);
//        when(productService.findById(1L)).thenReturn(Optional.of(product));
//
//        ResponseEntity<Product> response = productController.findById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(product, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        // when(productService.findById(1L)).thenReturn(Optional.empty());

        // ResponseEntity<Product> response = productController.findById(1L);

        // assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate() {
//        Product product = new Product("New Product", 200.0);
//        Product savedProduct = new Product("New Product", 200.0);
//        when(productService.create(product)).thenReturn(savedProduct);
//
//        ResponseEntity<Product> response = productController.create(product);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(savedProduct, response.getBody());
    }

    @Test
    void testUpdate() {
//        Product product = new Product("Updated Product", 250.0);
//        when(productService.update(product)).thenReturn(product);
//
//        ResponseEntity<Product> response = productController.update(1L, product);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(product, response.getBody());
    }

    @Test
    void testDeleteById() {
        // doNothing().when(productService).deleteById(1L);

        // ResponseEntity<Void> response = productController.deleteById(1L);

        // assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // verify(productService, times(1)).deleteById(1L);
    }
}
