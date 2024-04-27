package br.com.ada.locatecar.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private Long id;

    public JwtResponse(String accessToken, Long id) {
        this.token = accessToken;
        this.id = id;
    }
}
