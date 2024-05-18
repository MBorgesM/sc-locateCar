package br.com.ada.locatecar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car_status")
@Getter
@Setter
public class CarStatus {
    @Id
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_car_status_car"))
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;
}
