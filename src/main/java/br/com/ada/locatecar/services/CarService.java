package br.com.ada.locatecar.services;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CarService {
    private static final String URL = "https://freetestapi.com";
    private static final String URL_ALL_CARS = "/api/v1/cars";
    private static final String URL_CAR = "/api/v1/cars/{idCar}";

    @Autowired
    private CarRepository carRepository;

    public List<CarDto> getAllCar(){
        CarDto[] cars = WebClient.create(URL)
                .get()
                .uri(URL_ALL_CARS)
                .retrieve()
                .bodyToMono(CarDto[].class)
                .block();

        return List.of(Objects.requireNonNull(cars));
    }

    public CarDto getCarById(String idCar) {

        return WebClient.create(URL)
                .get()
                .uri(URL_CAR, idCar)
                .retrieve()
                .bodyToMono(CarDto.class)
                .block();
    }

    public List<CarDto> sugarCars() {
        List<CarDto> cars = this.getAllCar();
        return this.saveAll(cars);
    }

    private List<CarDto> saveAll(List<CarDto> cars) {
        return cars.stream()
                .map(this::save)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private CarDto save(CarDto carDto) {
        return this.carRepository.save(carDto);
    }

    public CarDto sugarCar(String idCar) {
        CarDto car = this.getCarById(idCar);
        return this.save(car);
    }

    public List<CarDto> getAllAvailableCars() {
        return carRepository.findAllAvailableCars();
    }

    public boolean checkIfCarIsAvailable(Long id) {
        return carRepository.isCarAvailable(id);
    }
}
