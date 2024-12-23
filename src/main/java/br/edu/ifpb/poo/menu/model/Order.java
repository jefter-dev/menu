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
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

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

        System.out.println(orderItems);
        sb.append("\nOrder Items:\n");
        if (orderItems != null && !orderItems.isEmpty()) {
            sb.append(String.format("| %-30s | %-10s | %-10s |\n", "Product Name", "Quantity", "Price"));

            BigDecimal grandTotal = BigDecimal.ZERO;
            int totalQuantity = 0;

            for (OrderItem item : orderItems) {
                String productName = item.getProduct() != null ? item.getProduct().getName() : "N/A";
                BigDecimal productPrice = item.getProduct() != null && item.getProduct().getPrice() != null
                        ? item.getProduct().getPrice()
                        : BigDecimal.ZERO;

                sb.append(String.format("| %-30s | %-10d | %-10s |\n",
                        productName,
                        item.getQuantity() != null ? item.getQuantity() : 0,
                        productPrice));

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