package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    @JsonView(Views.SimpleView.class)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "observations")
    @JsonView(Views.SimpleView.class)
    private String observations;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Cart(Client client) {
        this.client = client;
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
        sb.append("Cart Details:\n");
        sb.append(String.format("| %-5s | %-30s |\n", "ID", "Client"));
        sb.append(String.format("| %-5s | %-30s |\n", id, client != null ? client.getName() : "No Client"));

//        sb.append("\nTimestamps:\n");
//        sb.append(String.format("| %-20s | %-20s |\n", "Created At", "Updated At"));
//        sb.append(String.format("| %-20s | %-20s |\n", createdAt != null ? createdAt : "Not Provided", updatedAt != null ? updatedAt : "Not Provided"));

        return sb.toString();
    }

    public String toStringWithItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart Details:\n");
        sb.append(String.format("| %-5s | %-30s |\n", "ID", "Client"));
        sb.append(String.format("| %-5s | %-30s |\n", id, client != null ? client.getName() : "No Client"));

        sb.append("\nCart Items:\n");
        if (cartItems != null && !cartItems.isEmpty()) {
            BigDecimal totalPrice = BigDecimal.ZERO;
            int totalQuantity = 0;

            sb.append(String.format("| %-30s | %-10s | %-10s |\n", "Product Name", "Quantity", "Price"));
            for (CartItem item : cartItems) {
                String productName = item.getProduct() != null ? item.getProduct().getName() : "N/A";
                BigDecimal productPrice = item.getProduct() != null && item.getProduct().getPrice() != null
                        ? item.getProduct().getPrice()
                        : BigDecimal.ZERO;

                sb.append(String.format("| %-30s | %-10d | %-10s |\n",
                        productName,
                        item.getQuantity() != null ? item.getQuantity() : 0,
                        productPrice));

                // Exibe os adicionais do item
                if (item.getCartItemAdditionals() != null && !item.getCartItemAdditionals().isEmpty()) {
                    sb.append("  Additionals:\n");
                    sb.append(String.format("  | %-20s | %-10s | %-10s |\n", "Additional Name", "Quantity", "Price"));
                    for (CartItemAdditional additional : item.getCartItemAdditionals()) {
                        String additionalName = additional.getAdditional() != null ? additional.getAdditional().getName() : "N/A";
                        BigDecimal additionalPrice = additional.getAdditional() != null && additional.getAdditional().getPrice() != null
                                ? additional.getAdditional().getPrice()
                                : BigDecimal.ZERO;

                        sb.append(String.format("  | %-20s | %-10d | %-10s |\n",
                                additionalName,
                                additional.getQuantity() != null ? additional.getQuantity() : 0,
                                additionalPrice));

                        // Soma o preço dos adicionais ao preço total
                        totalPrice = totalPrice.add(additionalPrice.multiply(BigDecimal.valueOf(additional.getQuantity() != null ? additional.getQuantity() : 0)));
                    }
                }

                // Soma o preço total (quantidade * preço unitário)
                totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity() != null ? item.getQuantity() : 0)));

                // Soma a quantidade total de itens
                totalQuantity += item.getQuantity() != null ? item.getQuantity() : 0;
            }

            sb.append(String.format("Total Quantity: %d\n", totalQuantity));
            sb.append(String.format("Total Price: %s\n", totalPrice));
        } else {
            sb.append("No items in cart.\n");
        }

        return sb.toString();
    }

    public BigDecimal getTotal() {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            BigDecimal productPrice = item.getProduct() != null && item.getProduct().getPrice() != null
                    ? item.getProduct().getPrice()
                    : BigDecimal.ZERO;
            // Soma o preço total (quantidade * preço unitário)
            totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity() != null ? item.getQuantity() : 0)));

            // Soma o preço total dos adicionais
            for (CartItemAdditional itemAdditional : item.getCartItemAdditionals()) {
                BigDecimal additionalPrice = itemAdditional.getAdditional() != null && itemAdditional.getAdditional().getPrice() != null
                        ? itemAdditional.getAdditional().getPrice()
                        : BigDecimal.ZERO;
                totalPrice = totalPrice.add(additionalPrice.multiply(BigDecimal.valueOf(itemAdditional.getQuantity() != null ? itemAdditional.getQuantity() : 0)));
            }
        }

        return totalPrice;
    }
}