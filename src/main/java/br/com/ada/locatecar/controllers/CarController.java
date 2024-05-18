package br.com.ada.locatecar.controllers;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CarDto> sugarCarsWebFlux(){
        return carService.sugarCars();
    }

    @GetMapping("/{idCar}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto sugarCarWebFlux(@PathVariable String idCar){
        return carService.sugarCar(idCar);
    }

    @GetMapping("/available")
    public List<CarDto> getAllAvailableCars() {
        return carService.getAllAvailableCars();
    }
}
