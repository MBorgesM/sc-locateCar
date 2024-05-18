package br.com.ada.locatecar.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "devolution")
@Data
@NoArgsConstructor
public class Devolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rent_id", nullable = false, foreignKey = @ForeignKey(name = "FK_devolution_rent"))
    private Rent rent;

    @Column(name = "devolution_time", nullable = false)
    private LocalDateTime devolutionTime;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;
}
