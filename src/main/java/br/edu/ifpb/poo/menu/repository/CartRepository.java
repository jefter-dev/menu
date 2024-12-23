package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems WHERE c.id = :id")
    Optional<Cart> findByIdWithItems(@Param("id") Long id);
}