package br.com.ada.locatecar.services;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.repositories.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    @Spy
    private CarService carService;

    private static CarDto carMock;

    @BeforeAll
    static void initializeCars() {
        carMock = new CarDto();
        carMock.setId(1L);
        carMock.setMake("Toyota");
        carMock.setModel("Corolla");
        carMock.setYear(2022);
        carMock.setColor("Silver");
        carMock.setMileage(20000L);
        carMock.setPrice(new BigDecimal("25000"));
        carMock.setFuelType("Gasoline");
        carMock.setTransmission("Automatic");
        carMock.setEngine("2.0L 4-cylinder");
        carMock.setHorsepower(169);
        carMock.setOwners(1);
        carMock.setImage("https://fakeimg.pl/500x500/cccccc");
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCarList() {
        List<CarDto> result = carService.getAllCar();

        assertEquals(30, result.size());
    }

    @Test
    void testGetCarById() {
        CarDto result = carService.getCarById("1");

        assertNotNull(result);
        assertEquals(carMock, result);
    }

    @Test
    void testSugarCars() {
        List<CarDto> carList = List.of(carMock);
        when(carRepository.save(any(CarDto.class))).thenReturn(carMock);
        doReturn(carList).when(carService).getAllCar();

        List<CarDto> result = carService.sugarCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carMock, result.get(0));
    }

    @Test
    void testSugarCar() {
        when(carRepository.save(any(CarDto.class))).thenReturn(carMock);
        doReturn(carMock).when(carService).getCarById("1");

        CarDto result = carService.sugarCar("1");

        assertNotNull(result);
        assertEquals(carMock, result);
    }

    @Test
    void getAllAvailableCars() {
        List<CarDto> carList = List.of(carMock);
        when(carRepository.findAllAvailableCars()).thenReturn(carList);

        List<CarDto> result = carService.getAllAvailableCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carMock, result.get(0));
    }

    @Test
    void checkIfCarIsAvailable() {
        Long carId = 1L;
        when(carRepository.isCarAvailable(carId)).thenReturn(true);

        boolean result = carService.checkIfCarIsAvailable(carId);

        assertTrue(result);
    }
}