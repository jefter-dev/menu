package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(Views.SimpleView.class)
    private Long id;

    @Column(nullable = false, length = 100)
    @JsonView(Views.SimpleView.class)
    private String name;

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private String email;

    @Column(nullable = true, length = 255)
    private String password;

    @Embedded
    @JsonView(Views.SimpleView.class)
    private Address address;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
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
        sb.append("Person Details:\n");
        sb.append(String.format("| %-5s | %-20s | %-30s |\n", "ID", "Name", "Email"));
        sb.append(String.format("| %-5s | %-20s | %-30s |\n", id, name, email));

        return sb.toString();
    }

    public String toStringAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person Details:\n");
        sb.append(String.format("| %-5s | %-20s | %-30s |\n", "ID", "Name", "Email"));
        sb.append(String.format("| %-5s | %-20s | %-30s |\n", id, name, email));

        if (address != null) {
            sb.append(address.toString());  // Chama o toString da classe Address
        } else {
            sb.append("Address: Not Provided\n");
        }

        return sb.toString();
    }
}
