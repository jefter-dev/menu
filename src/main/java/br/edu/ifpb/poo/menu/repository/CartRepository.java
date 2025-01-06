package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("""
                SELECT c FROM Cart c
                LEFT JOIN FETCH c.cartItems ci
                LEFT JOIN FETCH ci.cartItemAdditionals cia
                LEFT JOIN FETCH cia.additional
                LEFT JOIN FETCH ci.product
                WHERE c.id = :cartId
            """)
    Cart findCartWithItemsAndProductsAndAdditional(@Param("cartId") Long cartId);

}