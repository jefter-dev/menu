package br.edu.ifpb.poo.menu.model;

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
    private String street;

    @Column(length = 9)
    private String postalCode;

    @Column(length = 20)
    private String number;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
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
