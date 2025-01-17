package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findByUserRegisterId(@Param("user_register") Long userRegister);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.operatingHours WHERE u.username = :username")
    User findByUsernameWithOperatingHours(@Param("username") String username);  // Método para buscar o usuário pelo username e carregar os operatingHours

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.operatingHours WHERE u.id = :id")
    User findByIdWithOperatingHours(@Param("id") Long id);
}