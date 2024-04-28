package br.com.ada.locatecar.payload.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Collection;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private Long id;
    private String email;
    private Collection<String> roles;

    public JwtResponse(String accessToken, Long id, String email, Collection<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
