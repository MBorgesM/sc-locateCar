package br.com.ada.locatecar.services;

import br.com.ada.locatecar.models.Devolution;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.repositories.DevolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DevolutionServiceTest {
    @Mock
    private DevolutionRepository devolutionRepository;

    @InjectMocks
    private DevolutionService devolutionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDevolveCarSuccessfully() {
        Rent rent = new Rent();
        LocalDateTime devolutionTime = LocalDateTime.now();
        BigDecimal totalValue = new BigDecimal("100.00");

        Devolution devolution = new Devolution();
        devolution.setRent(rent);
        devolution.setDevolutionTime(devolutionTime);
        devolution.setTotalValue(totalValue);

        when(devolutionRepository.save(any(Devolution.class))).thenReturn(devolution);

        Devolution result = devolutionService.devolveCar(rent, devolutionTime, totalValue);

        assertNotNull(result);
        assertEquals(rent, result.getRent());
        assertEquals(devolutionTime, result.getDevolutionTime());
        assertEquals(totalValue, result.getTotalValue());
        verify(devolutionRepository, times(1)).save(any(Devolution.class));
    }
}
