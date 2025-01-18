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

//teste

@Entity
@Table(name = "additional")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Additional {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @JsonView(Views.SimpleView.class)
    private String name;

    @Column(nullable = false, length = 255)
    @JsonView(Views.SimpleView.class)
    private String description;

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private BigDecimal price;

    @JsonView(Views.SimpleView.class)
    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @JsonView(Views.SimpleView.class)
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "additional", cascade = CascadeType.MERGE, orphanRemoval = false)
    private List<ProductAdditional> productAdditionals = new ArrayList<>();

//    @JsonView(Views.DetailedView.class)
    @OneToMany(mappedBy = "additional", cascade = CascadeType.MERGE, orphanRemoval = false)
    private List<CartItemAdditional> cartItemAdditionals = new ArrayList<>();

//    @JsonView(Views.DetailedView.class)
    @OneToMany(mappedBy = "additional", cascade = CascadeType.MERGE, orphanRemoval = false)
    private List<OrderItemAdditional> orderItemAdditionals = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
        sb.append("Additional Details:\n");
        sb.append(String.format("| %-5s | %-20s | %-10s |\n", "ID", "Name", "Price"));
        sb.append(String.format("| %-5s | %-20s | %-10.2f |\n", id, name, price));
        return sb.toString();
    }
}