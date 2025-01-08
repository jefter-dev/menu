package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonView(Views.DetailedView.class)
    private boolean admin;

    @Column(nullable = true, unique = true, length = 50)
    @JsonView(Views.SimpleView.class)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(Views.DetailedView.class)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(Views.DetailedView.class)
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(Views.DetailedView.class)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonView(Views.SimpleView.class)
    private List<OperatingHours> operatingHours = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_register")
    @JsonView(Views.DetailedView.class)
    private User userRegister;

    // Novos campos
    @Column(nullable = true, length = 100)
    @JsonView(Views.SimpleView.class)
    private String socialReason; // Razão Social

    @Column(nullable = true, length = 14, unique = true)
    @JsonView(Views.SimpleView.class)
    private String cnpj; // CNPJ

    // Construtor personalizado com os campos name, email, admin, razão social e cnpj
    public User(String name, String email, boolean admin, String socialReason, String cnpj) {
        super(name, email); // Chamando o construtor da classe pai (Person)
        this.admin = admin;
        this.socialReason = socialReason;
        this.cnpj = cnpj;
    }

    public void addOperatingHour(OperatingHours operatingHour) {
        operatingHour.setUser(this);
        this.operatingHours.add(operatingHour);
    }

    public void removeOperatingHour(OperatingHours operatingHour) {
        operatingHour.setUser(null);
        this.operatingHours.remove(operatingHour);
    }

    public String toStringDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person Details:\n");
        sb.append(String.format("| %-5s | %-20s | %-30s |\n", "ID", "Name", "Email"));
        sb.append(String.format("| %-5s | %-20s | %-30s |\n", this.getId(), this.getName(), this.getEmail()));

        if (this.getAddress() != null) {
            sb.append(this.getAddress()); // Chama o toString da classe Address
        } else {
            sb.append("Address: Not Provided\n");
        }

        // Adicionando Razão Social e CNPJ
        sb.append(String.format("| %-5s | %-20s |\n", "Social Reason", this.socialReason != null ? this.socialReason : "Not Provided"));
        sb.append(String.format("| %-5s | %-20s |\n", "CNPJ", this.cnpj != null ? this.cnpj : "Not Provided"));

        // Adicionando os horários de funcionamento
        sb.append("\nOperating Hours:\n");
        if (operatingHours != null && !operatingHours.isEmpty()) {
            sb.append(String.format("| %-10s | %-15s |\n", "Day", "Time"));
            sb.append("-".repeat(30)).append("\n");
            for (OperatingHours oh : operatingHours) {
                String day = (oh.getDayOfWeek() != null) ? oh.getDayOfWeek().toString() : "Unknown Day";
                String openTime = (oh.getOpeningTime() != null) ? oh.getOpeningTime().toString() : "Unknown Time";
                String closeTime = (oh.getClosingTime() != null) ? oh.getClosingTime().toString() : "Unknown Time";

                sb.append(String.format("| %-10s | %s - %-15s |\n", day, openTime, closeTime));
            }
        } else {
            sb.append("No Operating Hours Provided.\n");
        }

        return sb.toString();
    }
}