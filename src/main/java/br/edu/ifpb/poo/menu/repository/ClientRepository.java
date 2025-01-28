package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByName(String name);

    Client findByIdAndUserId(Long clientId, Long userId);

    List<Client> findByUserId(@Param("userId") Long userId);

    Client findByEmail(String email);

}