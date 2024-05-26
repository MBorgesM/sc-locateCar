package br.com.ada.locatecar.services;

import br.com.ada.locatecar.exceptions.CarAlreadyRentedException;
import br.com.ada.locatecar.models.CarStatus;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.repositories.CarRepository;
import br.com.ada.locatecar.repositories.CarStatusRepository;
import br.com.ada.locatecar.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarStatusRepository carStatusRepository;

    public Rent rentCar(Long carId, Long userId, LocalDateTime rentTime) {
        CarStatus carStatus = carStatusRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car status not found"));
        if (carStatus.getStatus().equals("Rented")) {
            throw new CarAlreadyRentedException();
        }
        carStatus.setStatus("Rented");
        carStatusRepository.save(carStatus);

        Rent rent = new Rent();
        rent.setCar(carRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Car not found")));
        rent.setUserId(userId);
        rent.setRentTime(rentTime);

        return rentRepository.save(rent);
    }

    public Optional<Rent> getRent(Long rentId) {
        return rentRepository.findById(rentId);
    }
}
