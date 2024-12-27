package br.edu.ifpb.poo.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Person {

    @Column(nullable = false)
    private boolean admin;

    //    @OneToMany(mappedBy = "user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    //    @OneToMany(mappedBy = "user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<>();

    //    @OneToMany(mappedBy = "user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_register")
    private User userRegister;

    // Construtor personalizado com os campos name, email e admin
    public User(String name, String email, boolean admin) {
        super(name, email); // Chamando o construtor da classe pai (Person)
        this.admin = admin;
    }
}