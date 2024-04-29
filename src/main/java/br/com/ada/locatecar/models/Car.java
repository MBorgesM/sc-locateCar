package br.com.ada.locatecar.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name  = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String model;

    @NotBlank
    private String brand;

    @NotBlank
    private BigDecimal price;

    @NotBlank
    private int year;

    public Car(Long id, String model, String brand, BigDecimal price, int year) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.year = year;
    }
}
