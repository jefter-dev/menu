package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Additional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional, Long> {
    List<Additional> findByName(String name);
}