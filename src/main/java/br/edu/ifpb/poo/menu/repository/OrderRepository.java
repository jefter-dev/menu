package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :id")
    Order findOrderWithItemsById(@Param("id") Long id);

    @Query("""
                SELECT o 
                FROM Order o
                LEFT JOIN FETCH o.orderItems oi
                LEFT JOIN FETCH oi.orderItemAdditional oia
                LEFT JOIN FETCH o.user u
                LEFT JOIN FETCH o.client c
                WHERE o.id = :id
            """)
    Order findOrderItemsAndProductsAndAdditional(@Param("id") Long id);

    @Query("""
                SELECT o 
                FROM Order o
                LEFT JOIN FETCH o.orderItems oi
                LEFT JOIN FETCH oi.orderItemAdditional oia
                LEFT JOIN FETCH o.user u
                LEFT JOIN FETCH o.client c
                WHERE o.client.id = :clientId
            """)
    List<Order> findOrdersByClientId(@Param("clientId") Long clientId);
}
