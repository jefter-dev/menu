package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "operating_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingHours {

    @Id
    @JsonView(Views.SimpleView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // Enum para representar os dias da semana

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private LocalTime openingTime; // Horário de abertura

    @Column(nullable = false)
    @JsonView(Views.SimpleView.class)
    private LocalTime closingTime; // Horário de fechamento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @JsonView(Views.SimpleView.class)
    private LocalDateTime updatedAt;

    public OperatingHours(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
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
}
