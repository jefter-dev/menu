package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.repository.AdditionalRepository;
import br.edu.ifpb.poo.menu.repository.CategoryRepository;
import br.edu.ifpb.poo.menu.repository.ProductAdditionalRepository;
import br.edu.ifpb.poo.menu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdditionalRepository additionalRepository;
    @Autowired
    private ProductAdditionalRepository productAdditionalRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retorna um produto pelo ID.
     */
    public Product getProductById(Long id) throws ProductNotFoundException {
        try {
            Optional<Product> productFind = productRepository.findById(id);
            if (productFind.isEmpty()) {
                throw new ProductNotFoundException("Produto inexistente.");
            }
            return productFind.get();
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Retorna todos os produtos.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Cria um novo produto e associa os adicionais ao produto.
     */
    @Transactional
    public Product createProduct(Product request) throws InvalidFieldException {
        // Valida os campos obrigatórios do produto
        validateProduct(request);

        // Cria a entidade Product e preenche os dados básicos
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setUser(request.getUser());
        product.setAdditionalQuantity(request.getAdditionalQuantity());
        product.setDiscount(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO);

        // Adiciona os adicionais diretamente
        if (request.getProductAdditionals() != null) {
            for (ProductAdditional additionalRequest : request.getProductAdditionals()) {
                if (additionalRequest.getAdditional() != null && additionalRequest.getAdditional().getId() != null) {
                    ProductAdditional productAdditional = new ProductAdditional();
                    productAdditional.setProduct(product);
                    productAdditional.setAdditional(additionalRequest.getAdditional());
                    product.getProductAdditionals().add(productAdditional);
                }
            }
        }

        // Adiciona as categorias diretamente
        if (request.getCategories() != null) {
            product.getCategories().addAll(request.getCategories());
        }

        // Salva o produto com os adicionais e categorias associados
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException, InvalidFieldException {
        // Recupera o produto existente
        Product existingProduct = getProductById(id);

        // Valida os campos obrigatórios do produto
        validateProduct(updatedProduct);

        // Atualiza os dados básicos do produto
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setAdditionalQuantity(updatedProduct.getAdditionalQuantity());
        existingProduct.setDiscount(updatedProduct.getDiscount() != null ? updatedProduct.getDiscount() : BigDecimal.ZERO);

        // Atualiza os adicionais
        productAdditionalRepository.deleteByProductId(updatedProduct.getId());
        if (updatedProduct.getProductAdditionals() != null) {
            existingProduct.getProductAdditionals().clear();
            for (ProductAdditional additionalRequest : updatedProduct.getProductAdditionals()) {
                if (additionalRequest.getAdditional() != null && additionalRequest.getAdditional().getId() != null) {
                    ProductAdditional productAdditional = new ProductAdditional();
                    productAdditional.setProduct(existingProduct);
                    productAdditional.setAdditional(additionalRequest.getAdditional());

                    existingProduct.getProductAdditionals().add(productAdditional);
                }
            }
        }

        // Atualiza as categorias
        existingProduct.getCategories().clear();
        if (updatedProduct.getCategories() != null) {
            for (Category category : updatedProduct.getCategories()) {
                existingProduct.getCategories().add(category);
            }
        }

        // Salva o produto com os novos adicionais e categorias
        try {
            return productRepository.save(existingProduct);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Deleta um produto pelo ID.
     */
    public void deleteProductById(Long id) throws ProductNotFoundException {
        Product product = getProductById(id);
        try {
            productRepository.delete(product);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Retorna detalhes de um produto com suas categorias associadas.
     */
    public Product getProductDetailsWithCategory(Product product) throws ProductNotFoundException {
        if (product == null) {
            throw new ProductNotFoundException("Produto não encontrado.");
        }
        return productRepository.findByIdWithCategories(product.getId());
    }

    /**
     * Busca produtos por nome e usuário.
     */
    public List<Product> searchByNameForUser(String name, User user) throws InvalidUserException, ProductNotFoundException {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductNotFoundException("O nome do produto não pode ser nulo ou vazio.");
        }

        if (user == null) {
            throw new InvalidUserException("Usuário fornecido não encontrado.");
        }

        try {
            return productRepository.findByNameAndUserId(name, user.getId());
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Retorna produtos vinculados a um usuário.
     */
    public List<Product> getProductsByUser(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("Usuário fornecido não encontrado.");
        }

        try {
            return productRepository.findProductsByUserId(user.getId());
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Valida os campos do produto.
     */
    private void validateProduct(Product product) throws InvalidFieldException {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidFieldException("O nome do produto não pode ser nulo ou vazio.");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidFieldException("O preço do produto deve ser maior que zero.");
        }
        if (product.getAdditionalQuantity() < 0) {
            throw new InvalidFieldException("A quantidade adicional não pode ser negativa.");
        }
    }

    public void uploadProductImage(Long id, MultipartFile file) throws ProductNotFoundException, InvalidFieldException {
        // Verifica se o produto existe
        Product product = getProductById(id);

        // Valida o arquivo
        if (file == null || file.isEmpty()) {
            throw new InvalidFieldException("O arquivo de imagem não pode estar vazio.");
        }

        try {
            // Alternativa usando um caminho absoluto, por exemplo no diretório home do usuário
            Path uploadDirectory = Paths.get(System.getProperty("user.dir"), "uploads", "product", product.getId().toString());

            System.out.println("uploadDirectory: " + uploadDirectory);

            // Verifica se o diretório existe, se não cria
            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }

            // Obtém o nome do arquivo original
            String imageName = file.getOriginalFilename();

            // Define o caminho completo para armazenar o arquivo
            Path filePath = uploadDirectory.resolve(imageName);

            // Salva o arquivo fisicamente no diretório
            file.transferTo(filePath.toFile());

            // Atualiza o produto com o nome do arquivo
            product.setImage(imageName);

            // Salva as alterações no produto
            productRepository.save(product);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem. Detalhes: " + e.getMessage(), e);
        }
    }

}
