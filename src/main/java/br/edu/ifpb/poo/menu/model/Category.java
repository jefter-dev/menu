package br.edu.ifpb.poo.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Category(String name, User user) {
        this.name = name;
        this.user = user;
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
        sb.append("Category Details:\n");
        sb.append(String.format("| %-5s | %-20s |\n", "ID", "Name"));
        sb.append(String.format("| %-5s | %-20s |\n", id, name));

        return sb.toString();
    }

    public String toStringWithProducts() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category Details:\n");
        sb.append(String.format("| %-5s | %-20s |\n", "ID", "Name"));
        sb.append(String.format("| %-5s | %-20s |\n", id, name));

        sb.append(String.format("| %-25s |\n", "----------------------------"));
        sb.append(String.format("| Products Count: %-12s |\n", (products != null ? products.size() : "N/A")));
        if(products != null && !products.isEmpty()) {
            AtomicInteger index = new AtomicInteger();
            products.forEach((product) -> {
                index.addAndGet(1);
                sb.append(String.format("| %-5s | %-20s |\n", index, product.getName() + " - R$ " + product.getPrice()));
            });
        }

        return sb.toString();
    }
}