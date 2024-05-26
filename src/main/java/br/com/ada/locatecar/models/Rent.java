package br.com.ada.locatecar.models;


import br.com.ada.locatecar.dtos.CarDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "FK_rent_car"))
    private CarDto car;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "rent_time", nullable = false)
    private LocalDateTime rentTime;
}
