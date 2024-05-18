package br.com.ada.locatecar.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DevolutionRequest {
    @NotBlank
    private Long rentId;

    @NotBlank
    private LocalDateTime devolutionTime;
}
