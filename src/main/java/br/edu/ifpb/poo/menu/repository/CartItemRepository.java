package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}