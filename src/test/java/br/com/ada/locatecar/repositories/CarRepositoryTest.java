package br.com.ada.locatecar.repositories;

import br.com.ada.locatecar.dtos.CarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql")
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        CarDto car1 = new CarDto();
        car1.setId(1L);
        car1.setMake("Toyota");
        car1.setModel("Corolla");
        car1.setYear(2020);
        car1.setColor("Red");
        car1.setMileage(15000L);
        car1.setPrice(new BigDecimal("20000"));
        car1.setFuelType("Gasoline");
        car1.setTransmission("Automatic");
        car1.setEngine("2.0L");
        car1.setHorsepower(150);
        car1.setOwners(1);
        car1.setImage("toyota_corolla.png");
        carRepository.save(car1);

        CarDto car2 = new CarDto();
        car2.setId(2L);
        car2.setMake("Honda");
        car2.setModel("Civic");
        car2.setYear(2019);
        car2.setColor("Blue");
        car2.setMileage(30000L);
        car2.setPrice(new BigDecimal("18000"));
        car2.setFuelType("Gasoline");
        car2.setTransmission("Manual");
        car2.setEngine("1.8L");
        car2.setHorsepower(140);
        car2.setOwners(2);
        car2.setImage("honda_civic.png");
        carRepository.save(car2);
    }


    @Test
    void testFindAllAvailableCars() {
        List<CarDto> availableCars = carRepository.findAllAvailableCars();
        assertThat(availableCars).hasSize(1);
        assertThat(availableCars.get(0).getMake()).isEqualTo("Toyota");
    }

    @Test
    void testCarAvailable() {
        boolean status = carRepository.isCarAvailable(1L);
        assertTrue(status);
    }

    @Test
    void testCarNotAvailable() {
        boolean status = carRepository.isCarAvailable(2L);
        assertFalse(status);
    }
}