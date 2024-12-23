package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Category;
import br.edu.ifpb.poo.menu.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);

    List<Category> findByUserId(@Param("userId") Long userId);
    // Ajuste para retornar uma lista de categorias
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.user.id = :userId")
    List<Category> findAllWithProductsByUserId(@Param("userId") Long userId);

}