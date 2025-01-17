package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = false, length = 255)
    private String name;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = true, length = 255)
    private String description;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = false)
    private BigDecimal price;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = true, length = 255)
    private String image;

    @JsonView(Views.SimpleView.class)
    @Column(nullable = true)
    private Integer additionalQuantity = 1;

    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonView(Views.ProductView.class)
    @JoinTable(name = "category_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
//    private List<Category> categories = new ArrayList<>();
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonView(Views.MenuView.class)
    private Set<ProductAdditional> productAdditionals = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product(String name, Double price, User user) {
        this.price = BigDecimal.valueOf(price);
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
        sb.append("Product Details:\n");
        sb.append(String.format("| %-5s | %-20s | %-10s |\n", "ID", "Name", "Price"));
        sb.append(String.format("| %-5s | %-20s | %-10s |\n", id, name, price));

        return sb.toString();
    }

}