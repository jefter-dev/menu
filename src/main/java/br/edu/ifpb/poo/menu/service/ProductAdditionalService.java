package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.repository.ProductAdditionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAdditionalService {
    @Autowired
    private ProductAdditionalRepository productAdditionalRepository;

    public List<Additional> getAdditionalsByProductId(Product product) throws ProductNotFoundException {
        if(product == null) {
            throw new ProductNotFoundException("Produto não encontrado.");
        }

        return productAdditionalRepository.findAdditionalsByProductId(product.getId());
    }
}
