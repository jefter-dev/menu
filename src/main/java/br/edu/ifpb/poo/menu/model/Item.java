package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // ou GenerationType.TABLE
    private Long id;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = false)
    protected Integer quantity;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = false)
    protected BigDecimal price;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

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