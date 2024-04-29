package br.com.ada.locatecar.repositories;

import br.com.ada.locatecar.dtos.CarDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarDto, Long> {
}
