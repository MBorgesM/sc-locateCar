package br.com.ada.locatecar.controllers;

import br.com.ada.locatecar.payload.request.LoginRequest;
import br.com.ada.locatecar.payload.request.SignupRequest;
import br.com.ada.locatecar.payload.response.JwtResponse;
import br.com.ada.locatecar.payload.response.MessageResponse;
import br.com.ada.locatecar.security.services.UserDetailsImpl;
import br.com.ada.locatecar.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        // Welcome the new user and explain the process
        System.out.println("Welcome, " + signupRequest.getUsername() + "!");
        System.out.println("We'll create your account to help you rent cars with ease.");

        try {
            authService.registerUser(signupRequest);
            return ResponseEntity.ok(new MessageResponse("Your account is ready! Welcome aboard."));
        } catch (RuntimeException e) {
            // Provide a more user-friendly error message
            String errorMessage = "Oops, something went wrong during registration. Please try again, or contact us if the issue persists.";
            return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Object[] items = authService.login(loginRequest);
        String jwt = (String) items[0];
        UserDetailsImpl userDetails = (UserDetailsImpl) items[1];

        // Craft a more welcoming login response
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), authorities));
    }
}
