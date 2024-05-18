package br.com.ada.locatecar.repositories;

import br.com.ada.locatecar.models.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
}
