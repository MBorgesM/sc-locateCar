package br.com.ada.locatecar.services;

import br.com.ada.locatecar.models.Devolution;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.repositories.DevolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DevolutionService {
    @Autowired
    private DevolutionRepository devolutionRepository;

    public Devolution devolveCar(Rent rent, LocalDateTime devolutionTime, BigDecimal totalValue) {
        Devolution devolution = new Devolution();
        devolution.setRent(rent);
        devolution.setDevolutionTime(devolutionTime);
        devolution.setTotalValue(totalValue);

        return devolutionRepository.save(devolution);
    }
}
