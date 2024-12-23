package br.edu.ifpb.poo.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "additional")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Additional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "additional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAdditional> productAdditionals = new ArrayList<>();

    @OneToMany(mappedBy = "additional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemAdditional> cartItemAdditionals = new ArrayList<>();

    @OneToMany(mappedBy = "additional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemAdditional> orderItemAdditionals = new ArrayList<>();

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