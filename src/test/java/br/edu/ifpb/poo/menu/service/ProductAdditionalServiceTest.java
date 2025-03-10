package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ProductAdditionalServiceTest {
    @Autowired
    private ProductAdditionalService productAdditionalService;

    @Test
    void getProductsAdditional() {
        try {
            Product product = new Product();
            product.setId(802L);

            List<Additional> productsAdditional = productAdditionalService.getAdditionalsByProductId(product);
            System.out.println(productsAdditional);

        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}