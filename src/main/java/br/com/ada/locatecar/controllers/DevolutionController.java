package br.com.ada.locatecar.controllers;

import br.com.ada.locatecar.models.Devolution;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.payload.request.DevolutionRequest;
import br.com.ada.locatecar.payload.response.MessageResponse;
import br.com.ada.locatecar.services.CarService;
import br.com.ada.locatecar.services.DevolutionService;
import br.com.ada.locatecar.services.RentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping("/api/devolution")
public class DevolutionController {
    @Autowired
    private RentService rentService;

    @Autowired
    private DevolutionService devolutionService;

    @PostMapping
    public ResponseEntity<?> devolveCar(@Valid @RequestBody DevolutionRequest devolutionRequest,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Rent> optionalRent = rentService.getRent(devolutionRequest.getRentId());
        if (optionalRent.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Rent not found"), HttpStatus.NOT_FOUND);
        }
        Rent rent = optionalRent.get();

        if (rent.getRentTime().isAfter(devolutionRequest.getDevolutionTime())) {
            return ResponseEntity.badRequest().body(new MessageResponse("The devolution time is invalid"));
        }

        long daysRented = calculateRentedTime(rent.getRentTime(), devolutionRequest.getDevolutionTime());
        BigDecimal totalValue = calculateRentTotalValue(daysRented, rent.getCar().getPrice());
        Devolution devolution = devolutionService.devolveCar(rent, devolutionRequest.getDevolutionTime(), totalValue);

        return ResponseEntity.ok(devolution);
    }

    private long calculateRentedTime(LocalDateTime rentTime, LocalDateTime devolutionTime) {
        long days = ChronoUnit.DAYS.between(rentTime, devolutionTime);
        if (devolutionTime.isAfter(rentTime)) {
            days += 1;
        }
        return days;
    }

    private BigDecimal calculateRentTotalValue(long numberOfDaysRented, BigDecimal carPrice) {
        int dailyTax;

        if (carPrice.compareTo(new BigDecimal("35000")) < 0) {
            dailyTax = 100;
        } else if (carPrice.compareTo(new BigDecimal("45000")) < 0) {
            dailyTax = 150;
        } else {
            dailyTax = 200;
        }

        return BigDecimal.valueOf(dailyTax * numberOfDaysRented);
    }
}
