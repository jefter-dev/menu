package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findByUserRegisterId(@Param("user_register") Long userRegister);

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userRegister.id = :userRegisterId")
    List<User> findAllByUserRegisterId(@Param("userRegisterId") Long userRegisterId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.operatingHours WHERE u.username = :username ORDER BY u.id DESC")
    User findByUsernameWithOperatingHours(@Param("username") String username);  // Método para buscar o usuário pelo username e carregar os operatingHours

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.operatingHours WHERE u.id = :id")
    User findByIdWithOperatingHours(@Param("id") Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.operatingHours WHERE u.id = :id")
    User findByIdWithOperatingHoursAndCategories(@Param("id") Long id);

    User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.operatingHours WHERE u.email = :email")
    User findByEmailWithOperatingHours(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.operatingHours ")
    List<User> findAllWithDetails();

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.operatingHours " +
            "WHERE (:admin IS NULL OR u.admin = :admin)")
    List<User> findAllWithDetailsByAdmin(@Param("admin") Boolean admin);

    @Query("SELECT c FROM Client c WHERE c.user.id = :userId")
    List<Client> findClientsByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.productAdditionals pa " +
            "LEFT JOIN FETCH pa.additional " +
            "LEFT JOIN FETCH p.categories c " +
            "WHERE p.user.id = :userId")
    List<Product> getProductsByUserIdWithAdditionals(@Param("userId") Long userId);

    @Query("SELECT a FROM Additional a WHERE a.user.id = :userId")
    List<Additional> getAdditionalsByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Category a WHERE a.user.id = :userId")
    List<Category> getCatgegoriesByUserId(@Param("userId") Long userId);

    @Query("""
                SELECT DISTINCT o
                FROM Order o
                LEFT JOIN FETCH o.orderItems oi
                LEFT JOIN FETCH oi.product p
                LEFT JOIN FETCH oi.orderItemAdditional oia
                LEFT JOIN FETCH oia.additional a
                LEFT JOIN FETCH o.client c
                WHERE o.user.id = :userId
            """)
    List<Order> findOrdersByUserIdWithDetails(@Param("userId") Long userId);


    @Query("""
                SELECT DISTINCT o
                FROM Order o
                LEFT JOIN FETCH o.orderItems oi
                LEFT JOIN FETCH oi.product p
                LEFT JOIN FETCH oi.orderItemAdditional oia
                LEFT JOIN FETCH oia.additional a
                LEFT JOIN FETCH o.client c
                WHERE o.user.id = :userId
                  AND o.status = :status
            """)
    List<Order> findOrdersByUserIdAndStatusWithDetails(@Param("userId") Long userId, @Param("status") String status);
}