package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
public class Client extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(Views.DetailedView.class)
    @JoinColumn(name = "user_id")
    private User user;

    // Construtor personalizado com os campos name e email
    public Client(String name, String email, User user) {
        super(name, email); // Chamando o construtor da classe pai (Person)

        this.user = user;
    }

    public Client() {

    }
}
