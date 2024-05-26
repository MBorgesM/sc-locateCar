package br.com.ada.locatecar.services;

import br.com.ada.locatecar.exceptions.DocumentInUseException;
import br.com.ada.locatecar.exceptions.EmailInUseException;
import br.com.ada.locatecar.models.ERole;
import br.com.ada.locatecar.models.Role;
import br.com.ada.locatecar.payload.request.LoginRequest;
import br.com.ada.locatecar.payload.request.SignupRequest;
import br.com.ada.locatecar.repositories.RoleRepository;
import br.com.ada.locatecar.repositories.UserRepository;
import br.com.ada.locatecar.security.jwt.JwtUtils;
import br.com.ada.locatecar.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Autowired
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserWithEmailThatAlreadyExists() {
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        SignupRequest request = new SignupRequest();
        request.setDocument("49208756815");
        request.setEmail("test@gmail.com");
        request.setPassword("123456");
        request.setRole(roles);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(EmailInUseException.class, () -> authService.registerUser(request));
    }

    @Test
    void testRegisterUserWithDocumentThatAlreadyExists() {
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        SignupRequest request = new SignupRequest();
        request.setDocument("49208756815");
        request.setEmail("test@gmail.com");
        request.setPassword("123456");
        request.setRole(roles);

        when(userRepository.existsByDocument(request.getDocument())).thenReturn(true);

        assertThrows(DocumentInUseException.class, () -> authService.registerUser(request));
    }

    @Test
    void testRegisterUserSuccessfully() {
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        SignupRequest request = new SignupRequest();
        request.setDocument("49208756815");
        request.setEmail("test@gmail.com");
        request.setPassword("123456");
        request.setRole(roles);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByDocument(request.getDocument())).thenReturn(false);
        when(roleRepository.findByName(ERole.USER)).thenReturn(Optional.of(new Role(1, ERole.USER)));

        assertDoesNotThrow(() -> authService.registerUser(request));
    }

    @Test
    void testLoginSuccessfully() {
        LoginRequest login = new LoginRequest();
        login.setEmail("test@gmail.com");
        login.setPassword("123456");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwtToken");
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        Object[] result = authService.login(login);

        assertNotNull(result);
        assertEquals("jwtToken", result[0]);
        assertEquals(userDetails, result[1]);
    }

    @Test
    void testLoginFailed() {
        LoginRequest login = new LoginRequest();
        login.setEmail("test@gmail.com");
        login.setPassword("123456");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authService.login(login));
    }
}