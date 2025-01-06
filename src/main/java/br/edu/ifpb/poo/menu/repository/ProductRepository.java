package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findAllByUserId(Long userId);

    List<Product> findByUserId(@Param("userId") Long userId);

    @Transactional
    @JsonView(Views.SimpleView.class)
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories WHERE p.id = :id")
    Product findByIdWithCategories(@Param("id") Long id);

    @Transactional
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories WHERE p.name = :name AND p.user.id = :userId")
    List<Product> findByNameAndUserId(@Param("name") String name, @Param("userId") Long userId);
}