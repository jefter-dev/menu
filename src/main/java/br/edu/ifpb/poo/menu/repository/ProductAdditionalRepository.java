package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.ProductAdditional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAdditionalRepository extends JpaRepository<ProductAdditional, Long> {
    @Query("SELECT pa.additional FROM ProductAdditional pa WHERE pa.product.id = :productId")
    List<Additional> findAdditionalsByProductId(@Param("productId") Long productId);
}