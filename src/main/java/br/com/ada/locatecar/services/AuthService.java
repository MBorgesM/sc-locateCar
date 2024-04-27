package br.com.ada.locatecar.services;

import br.com.ada.locatecar.exceptions.DocumentInUseException;
import br.com.ada.locatecar.exceptions.EmailInUseException;
import br.com.ada.locatecar.models.ERole;
import br.com.ada.locatecar.models.Role;
import br.com.ada.locatecar.models.User;
import br.com.ada.locatecar.payload.request.LoginRequest;
import br.com.ada.locatecar.payload.request.SignupRequest;
import br.com.ada.locatecar.repositories.RoleRepository;
import br.com.ada.locatecar.repositories.UserRepository;
import br.com.ada.locatecar.security.jwt.JwtUtils;
import br.com.ada.locatecar.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailInUseException("User not created: Email is already in use!");
        }
        if (userRepository.existsByDocument(signupRequest.getDocument())) {
            throw new DocumentInUseException("User not created: Document is already in use!");
        }

        User user = new User(signupRequest.getDocument(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public Object[] login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new Object[]{jwt, userDetails};
    }
}
