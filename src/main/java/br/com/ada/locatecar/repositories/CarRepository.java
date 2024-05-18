package br.com.ada.locatecar.repositories;

import br.com.ada.locatecar.dtos.CarDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarDto, Long> {
    @Query("SELECT c FROM CarDto c JOIN CarStatus cs ON c.id = cs.id WHERE cs.status = 'Available'")
    List<CarDto> findAllAvailableCars();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM CarDto c JOIN CarStatus cs ON c.id = cs.id " +
            "WHERE c.id = :id AND cs.status = 'Available'")
    boolean isCarAvailable(Long id);
}
