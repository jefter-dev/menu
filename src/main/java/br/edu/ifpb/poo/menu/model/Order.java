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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonView(Views.SimpleView.class)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private BigDecimal total;

    @Column(name = "status")
    @JsonView(Views.SimpleView.class)
    private String status;

    @Column(name = "observations")
    @JsonView(Views.SimpleView.class)
    private String observations;

    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.SimpleView.class)
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order(BigDecimal total, String status, Client client, User user) {
        this.total = total;
        this.status = status;
        this.client = client;
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
        sb.append("Order Summary:\n");
        sb.append(String.format("| %-5s | %-20s | %-10s | %-15s | %-20s |\n",
                "ID", "Client", "Total", "Status", "Created At"));
        sb.append(String.format("| %-5s | %-20s | %-10s | %-15s | %-20s |\n",
                id,
                client != null ? client.getName() : "No Client",
                total,
                status,
                createdAt != null ? createdAt.toString() : "N/A"));
        return sb.toString();
    }

    public String toStringWithItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Details:\n");
        sb.append(String.format("| %-5s | %-20s | %-10s | %-15s |\n", "ID", "Client", "Total", "Status"));
        sb.append(String.format("| %-5s | %-20s | %-10s | %-15s |\n",
                id,
                client != null ? client.getName() : "No Client",
                total,
                status));

        sb.append("\nOrder Items:\n");
        if (orderItems != null && !orderItems.isEmpty()) {
            sb.append(String.format("| %-30s | %-10s | %-10s |\n", "Product Name", "Quantity", "Price"));

            BigDecimal grandTotal = BigDecimal.ZERO;
            int totalQuantity = 0;

            for (OrderItem item : orderItems) {
                String productName = item.getProduct() != null ? item.getProduct().getName() : "N/A";
                BigDecimal productPrice = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;

                sb.append(String.format("| %-30s | %-10d | %-10s |\n",
                        productName,
                        item.getQuantity() != null ? item.getQuantity() : 0,
                        productPrice));

                // Exibir adicionais do item, se houver
                if (item.getOrderItemAdditional() != null && !item.getOrderItemAdditional().isEmpty()) {
                    sb.append("  Additionals:\n");
                    sb.append(String.format("  | %-20s | %-10s | %-10s |\n", "Additional Name", "Quantity", "Price"));

                    for (OrderItemAdditional additional : item.getOrderItemAdditional()) {
                        String additionalName = additional.getAdditional() != null ? additional.getAdditional().getName() : "N/A";
                        BigDecimal additionalPrice = additional.getPrice() != null ? additional.getPrice() : BigDecimal.ZERO;

                        sb.append(String.format("  | %-20s | %-10d | %-10s |\n",
                                additionalName,
                                additional.getQuantity() != null ? additional.getQuantity() : 0,
                                additionalPrice));

                        // Soma o preço dos adicionais ao total geral
                        grandTotal = grandTotal.add(additionalPrice.multiply(BigDecimal.valueOf(additional.getQuantity() != null ? additional.getQuantity() : 0)));
                    }
                }

                // Soma o preço total (quantidade * preço unitário)
                grandTotal = grandTotal.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity() != null ? item.getQuantity() : 0)));

                // Soma a quantidade total de itens
                totalQuantity += item.getQuantity() != null ? item.getQuantity() : 0;
            }

            sb.append(String.format("Total Quantity: %d\n", totalQuantity));
            sb.append(String.format("Grand Total: %s\n", grandTotal));
        } else {
            sb.append("No items in order.\n");
        }

        return sb.toString();
    }
}