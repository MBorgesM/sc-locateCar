package br.com.ada.locatecar.controllers;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.services.CarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    private static CarDto car1;
    private static CarDto car2;

    @BeforeAll
    static void setupCars() {
        car1 = new CarDto();
        car1.setId(1L);
        car1.setMake("Toyota");
        car1.setModel("Camry");
        car1.setYear(2021);
        car1.setColor("Red");
        car1.setMileage(10000L);
        car1.setPrice(BigDecimal.valueOf(25000));
        car1.setFuelType("Gasoline");
        car1.setTransmission("Automatic");
        car1.setEngine("2.5L I4");
        car1.setHorsepower(203);
        car1.setOwners(1);
        car1.setImage("image_url_1");

        car2 = new CarDto();
        car2.setId(2L);
        car2.setMake("Honda");
        car2.setModel("Civic");
        car2.setYear(2020);
        car2.setColor("Blue");
        car2.setMileage(15000L);
        car2.setPrice(BigDecimal.valueOf(20000));
        car2.setFuelType("Gasoline");
        car2.setTransmission("Manual");
        car2.setEngine("2.0L I4");
        car2.setHorsepower(158);
        car2.setOwners(1);
        car2.setImage("image_url_2");
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testSugarCarsWebFlux() throws Exception {
        List<CarDto> carList = List.of(car1, car2);

        when(carService.sugarCars()).thenReturn(carList);

        System.out.println(carList.toString());

        mockMvc.perform(get("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carList.toString()))
                .andExpect(status().isOk());

        verify(carService, times(1)).sugarCars();
    }

    @Test
    void testSugarCarWebFlux() throws Exception {
        when(carService.sugarCar(anyString())).thenReturn(car1);

        mockMvc.perform(get("/api/car/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(carService, times(1)).sugarCar("1");
    }

    @Test
    void testGetAllAvailableCars() throws Exception {
        List<CarDto> carList = List.of(car1, car2);

        when(carService.getAllAvailableCars()).thenReturn(carList);

        mockMvc.perform(get("/api/car/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(carService, times(1)).getAllAvailableCars();
    }
}
