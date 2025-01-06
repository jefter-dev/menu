package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);

    @Query("""
                SELECT DISTINCT ci FROM CartItem ci
                LEFT JOIN FETCH ci.cartItemAdditionals cia
                LEFT JOIN FETCH cia.additional
                LEFT JOIN FETCH ci.product
                WHERE ci.cart.id = :cartId
            """)
    List<CartItem> findItemsWithProductsAndAdditional(@Param("cartId") Long cartId);

}