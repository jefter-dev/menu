package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.CartItemAdditional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemAdditionalRepository extends JpaRepository<CartItemAdditional, Long> {
}