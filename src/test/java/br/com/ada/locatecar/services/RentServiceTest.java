package br.com.ada.locatecar.services;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.exceptions.CarAlreadyRentedException;
import br.com.ada.locatecar.models.CarStatus;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.repositories.CarRepository;
import br.com.ada.locatecar.repositories.CarStatusRepository;
import br.com.ada.locatecar.repositories.RentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RentServiceTest {
    @Mock
    private RentRepository rentRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarStatusRepository carStatusRepository;

    @Autowired
    @InjectMocks
    private RentService rentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRentCarThatIsAlreadyRented() {
        CarStatus rentedStatus = new CarStatus(1L, "Rented");

        when(carStatusRepository.findById(1L)).thenReturn(Optional.of(rentedStatus));

        assertThrows(CarAlreadyRentedException.class, () -> rentService.rentCar(1L, 1L, LocalDateTime.now()));
    }

    @Test
    void testRentCarSuccessfully() {
        CarDto carMock = fillCarDtoMock();
        Long userId = 1L;
        LocalDateTime rentTime = LocalDateTime.now();
        Rent rent = new Rent(1L, carMock, userId, rentTime);
        CarStatus rentedStatus = new CarStatus(1L, "Available");

        when(carStatusRepository.findById(1L)).thenReturn(Optional.of(rentedStatus));
        when(carRepository.findById(carMock.getId())).thenReturn(Optional.of(carMock));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);

        Rent result = rentService.rentCar(carMock.getId(), userId, rentTime);

        assertNotNull(result);
        assertEquals(carMock.getId(), result.getCar().getId());
        assertEquals(userId, result.getUserId());
        assertEquals(rentTime, result.getRentTime());
        verify(carStatusRepository).save(rentedStatus);
    }

    @Test
    void testRentCarStatusNotFound() {
        Long carId = 1L;
        Long userId = 1L;
        LocalDateTime rentTime = LocalDateTime.now();

        when(carStatusRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> rentService.rentCar(carId, userId, rentTime));
        verify(carStatusRepository, never()).save(any(CarStatus.class));
        verify(rentRepository, never()).save(any(Rent.class));
    }

    @Test
    void testGetRent() {
        Long rentId = 1L;
        Rent rent = new Rent();
        rent.setId(rentId);

        when(rentRepository.findById(rentId)).thenReturn(Optional.of(rent));

        Optional<Rent> result = rentService.getRent(rentId);

        assertTrue(result.isPresent());
        assertEquals(rentId, result.get().getId());
    }

    @Test
    void testGetRentNotFound() {
        Long rentId = 1L;

        when(rentRepository.findById(rentId)).thenReturn(Optional.empty());

        Optional<Rent> result = rentService.getRent(rentId);

        assertFalse(result.isPresent());
    }

    private CarDto fillCarDtoMock() {
        CarDto carMock = new CarDto();
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

        return carMock;
    }
}