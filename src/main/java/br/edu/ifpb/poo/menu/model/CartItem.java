package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JsonView(Views.SimpleView.class)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private Integer quantity;

    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cartItem", fetch = FetchType.LAZY)
    @JsonView(Views.SimpleView.class)
    private Set<CartItemAdditional> cartItemAdditionals = new HashSet<>();

    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart Item Details:\n");
        sb.append(String.format("| %-20s | %-10s |\n", "Product Name", "Quantity"));
        sb.append(String.format("| %-20s | %-10s |\n",
                product != null ? product.getName() : "N/A",
                quantity != null ? quantity.toString() : "N/A"));
        return sb.toString();
    }
}