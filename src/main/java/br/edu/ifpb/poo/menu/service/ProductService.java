package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exceptions.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exceptions.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements CrudService<Product> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product contact) {
        return productRepository.save(contact);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {

        return productRepository.findAll();
    }

    public Optional<Product> searchByName(String name, User user) throws InvalidUserException, ProductNotFoundException {
        if (name == null) {
            throw new ProductNotFoundException("Nome não passado como parâmetro.");
        }

        if (user == null) {
            throw new InvalidUserException("Usuário fornecido não encontrado.");
        }

        return productRepository.findByNameAndUserId(name, user.getId());
    }

//    public List<Product> findProductsByProduct(Product product) {
//        return productRepository.findByProduct(product);
//    }

    @Override
    public Product update(Product contact) {

        return productRepository.save(contact);
    }

    @Override
    public void deleteById(Long id) {

        productRepository.deleteById(id);
    }

}