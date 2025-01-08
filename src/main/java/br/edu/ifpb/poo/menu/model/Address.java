package br.edu.ifpb.poo.menu.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Column(length = 255)
    @JsonView(Views.SimpleView.class)
    private String street;

    @Column(length = 9)
    @JsonView(Views.SimpleView.class)
    private String postalCode;

    @Column(length = 20)
    @JsonView(Views.SimpleView.class)
    private String number;

    @Column(length = 50)
    @JsonView(Views.SimpleView.class)
    private String city;

    @Column(length = 50)
    @JsonView(Views.SimpleView.class)
    private String referencePoint;

    public Address(String street, String postalCode, String number, String city, String referencePoint) {
        this.street = street;
        this.postalCode = postalCode;
        this.number = number;
        this.city = city;
        this.referencePoint = referencePoint;
    }

    public Address(String street, String postalCode, String number, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.number = number;
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format("Address:\n| %-20s | %-10s | %-10s | %-10s | %-10s |",
                street != null ? street : "-",
                postalCode != null ? postalCode : "-",
                number != null ? number : "-",
                city != null ? city : "-",
                referencePoint != null ? referencePoint : "-");
    }

}
