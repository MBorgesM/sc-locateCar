package br.com.ada.locatecar.controllers;

import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.payload.request.RentRequest;
import br.com.ada.locatecar.payload.response.MessageResponse;
import br.com.ada.locatecar.services.CarService;
import br.com.ada.locatecar.services.RentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rent")
public class RentController {
    @Autowired
    private CarService carService;

    @Autowired
    private RentService rentService;

    @PostMapping
    public ResponseEntity<?> rentCar(@Valid @RequestBody RentRequest rentRequest,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        if (!carService.checkIfCarIsAvailable(rentRequest.getCarId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("This car is already rented"));
        }

        Rent rent = rentService.rentCar(
                rentRequest.getCarId(), Long.parseLong(userDetails.getUsername()), rentRequest.getRentTime());
        return ResponseEntity.ok(rent);
    }
}
