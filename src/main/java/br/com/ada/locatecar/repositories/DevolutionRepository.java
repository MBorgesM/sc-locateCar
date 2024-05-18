package br.com.ada.locatecar.repositories;

import br.com.ada.locatecar.models.Devolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolutionRepository extends JpaRepository<Devolution, Long> {
}
