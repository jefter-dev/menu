package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> productFind = productRepository.findById(id);
        if(productFind.isEmpty()) {
            throw new ProductNotFoundException("Produto inexistente.");
        }

        try {
            return productFind.orElse(null);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) throws ProductNotFoundException {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public Product getProductDetailsWithCategory(Product product) throws ProductNotFoundException {
        if (product == null) {
            throw new ProductNotFoundException("Produto não encontrado.");
        }

        return productRepository.findByIdWithCategories(product.getId());
    }

    public List<Product> searchByNameForUser(String name, User user) throws InvalidUserException, ProductNotFoundException {
        if (name == null) {
            throw new ProductNotFoundException("Produto não encontrado.");
        }

        if (user == null) {
            throw new InvalidUserException("Usuário fornecido não encontrado.");
        }

        return productRepository.findByNameAndUserId(name, user.getId());
    }

}