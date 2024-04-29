package br.com.ada.locatecar.controllers;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CarDto> sugarCarsWebFlux(){
        List<CarDto> cars = carService.sugarDistritos();
        return cars;
    }
}
