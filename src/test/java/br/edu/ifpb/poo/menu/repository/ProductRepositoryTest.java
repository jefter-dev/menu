package br.edu.ifpb.poo.menu.repository;


import br.edu.ifpb.poo.menu.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdditionalRepository additionalRepository;

    @Autowired
    private ProductAdditionalRepository productAdditionalRepository;

//    pg_dump -U postgres -h localhost -p 5432 -d menu > "C:\Users\jefte\OneDrive\Documents\bancos teste\menu.sql"

    @Test
    public void shouldSaveProduct() {
        Category category = categoryRepository.findById(2L).orElse(null);
//        System.out.println(category); // DEBUG

        User userAdmin = userRepository.findById(302L).orElse(null); // USER ADMIN
        assertNotNull(userAdmin, "Category should not be null");
//        System.out.println(userAdmin); // DEBUG

        assertNotNull(category, "Category should not be null");

        String newNameProduct = "Tapioca";

        Product product = new Product(newNameProduct, 45.0, userAdmin);

        // Adiciona a categoria à lista
        product.getCategories().add(category);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals(newNameProduct, savedProduct.getName(), "The product name should match the expected value");
    }

    @Test
    public void shouldSaveProductWithAdditions() {
        // Encontre a categoria e o usuário, conforme já feito anteriormente
        Category category = categoryRepository.findById(2L).orElse(null);
        User userAdmin = userRepository.findById(302L).orElse(null);
        assertNotNull(userAdmin, "User should not be null");
        assertNotNull(category, "Category should not be null");

        // Crie um novo produto
        String newNameProduct = "Tapioca";
        Product product = new Product(newNameProduct, 45.0, userAdmin);
        product.getCategories().add(category);  // Associa categoria ao produto

        // Salve o produto primeiro
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId(), "Product ID should not be null after saving");

        // Crie um novo adicional
        Additional additional = new Additional();
        additional.setName("Queijo");
        additional.setDescription("Queijo ralado adicional");
        additional.setPrice(BigDecimal.valueOf(2));
        additional = additionalRepository.save(additional);  // Salve o adicional

        // Associe o adicional ao produto
        ProductAdditional productAdditional = new ProductAdditional();
        productAdditional.setProduct(savedProduct);  // Use o produto salvo
        productAdditional.setAdditional(additional);
        productAdditional.setCreatedAt(LocalDateTime.now());
        productAdditional.setUpdatedAt(LocalDateTime.now());

        // Salve o relacionamento entre produto e adicional
        productAdditionalRepository.save(productAdditional);

        // Verifique se o adicional foi associado ao produto
        assertNotNull(savedProduct.getProductAdditionals());
//        assertTrue(savedProduct.getProductAdditionals().size() > 0, "The product should have at least one additional");
    }

    @Test
    public void shouldGetProduct() {
        Product product = productRepository.findById(802L).orElse(null);
        assertNotNull(product, "Product should not be null");
        System.out.println(product); // Vai chamar toString sem carregar categorias
    }

    @Test
    public void shouldGetProductWithCategories() {
        Product product = productRepository.findByIdWithCategories(802L).orElse(null);
        assertNotNull(product, "Product should not be null");
        System.out.println(product); // Chama toString e carrega categorias
    }

//    @Test
//    public void shouldUpdateProductCategories() {
//        // Encontre o produto existente pelo ID (substitua 552L pelo ID do produto que deseja testar)
//        Product product = productRepository.findById(602L).orElse(null);
//        assertNotNull(product, "Product should not be null");
//
//        // Verifique a lista de categorias antes da atualização
//        int initialCategoryCount = product.getCategories().size();
//
//        // Encontre ou crie uma nova categoria para adicionar ao produto
//        Category newCategory = categoryRepository.findById(3L).orElse(null);
//        assertNotNull(newCategory, "New category should not be null");
//
//        // Adicione a nova categoria ao produto
//        product.getCategories().add(newCategory);
//
//        // Salve o produto atualizado no banco de dados
//        Product updatedProduct = productRepository.save(product);
//
//        // Verifique se a lista de categorias foi atualizada corretamente
//        assertEquals(initialCategoryCount + 1, updatedProduct.getCategories().size(),
//                "The category count should increase by one after adding a new category");
//
//        // Verifique se a nova categoria está presente na lista de categorias do produto atualizado
//        assertNotNull(updatedProduct.getCategories().stream()
//                .filter(category -> category.getId().equals(newCategory.getId()))
//                .findFirst()
//                .orElse(null), "The new category should be present in the product categories");
//    }

    @Test
    public void shouldDeleteProduct() {
        // Crie e salve um novo produto
        Product product = new Product();
        product.setName("Product Delete");
        product.setPrice(BigDecimal.valueOf(20));

        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId(), "Product ID should not be null after saving");

        // Confirme que o produto foi salvo
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertNotNull(foundProduct, "Product should exist before deletion");

        // Delete o produto
        productRepository.delete(savedProduct);

        // Verifique se o produto foi deletado
        Product deletedProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertEquals(null, deletedProduct, "Product should be null after deletion");
    }

    @Test
    public void shouldListAllProductsByUser() {
        // Supondo que o usuário com ID 1 já existe e possui produtos no banco
        Long userId = 302L;

        // Busque todos os produtos associados ao usuário
        List<Product> products = productRepository.findAllByUserId(userId);
        System.out.println(products);
    }
}
