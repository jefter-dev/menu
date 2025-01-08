package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_item_additional")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemAdditional {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @ManyToOne
    @JsonView(Views.SimpleView.class)
    @JoinColumn(name = "additional_id", nullable = false)
    private Additional additional;

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private Integer quantity;

    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime updatedAt;

    public CartItemAdditional(CartItem cartItem, Additional additional, Integer quantity) {
        this.cartItem = cartItem;
        this.additional = additional;
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
}