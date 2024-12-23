package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.ProductAdditional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAdditionalRepository extends JpaRepository<ProductAdditional, Long> {
//    List<ProductAdditional> findByName(String name);
}