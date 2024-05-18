package br.com.ada.locatecar.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentRequest {
    @NotBlank
    private Long carId;

    @NotBlank
    private LocalDateTime rentTime;
}
