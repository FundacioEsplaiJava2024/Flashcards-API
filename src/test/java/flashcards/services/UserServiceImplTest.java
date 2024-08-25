package flashcards.services;

import flashcards.entities.User;
import flashcards.exceptions.customexceptions.UserAlreadyExistsException;
import flashcards.repos.UserRepositoryJdbc;
import flashcards.security.JwtService;
import flashcards.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepositoryJdbc userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    private static User testUser;
    private static User newUser;

    @BeforeAll
    public static void init() {
        testUser = User.builder()
                .id(1)
                .username("testUser")
                .email("test@gmail.com")
                .password("password")
                .enabled(true)
                .build();

        newUser = User.builder()
                .username("newTestUser")
                .email("newTest@gmail.com")
                .password("password")
                .enabled(false)
                .build();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserSuccess() {
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(null);
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.addUser(any(User.class))).thenReturn(1);

        User createdUser = userService.createUser(newUser.getUsername()
                , newUser.getEmail(), newUser.getPassword());

        assertEquals("newTestUser", createdUser.getUsername());
        assertEquals("newTest@gmail.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        assertEquals(1, createdUser.getId());
        verify(userRepository).addUser(any(User.class));
    }

    @Test
    public void testCreateUserUsernameExists() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);
        assertThrows(UserAlreadyExistsException.class, () ->
                userService.createUser(testUser.getUsername(), "newTest@gmail.com"
                        , "password"));
    }

    @Test
    public void testCreateUserEmailExists() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
        assertThrows(UserAlreadyExistsException.class, () ->
                userService.createUser("testUser", testUser.getEmail(), "password"));
    }

    @Test
    public void testLoginSuccess() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(testUser
                        , null, null));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        String token = userService.login(testUser.getEmail(), testUser.getPassword());

        assertNotNull(token);
        assertEquals("jwtToken", token);
    }

    @Test
    public void testLoginInvalidEmail() {
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(null);
        assertThrows(BadCredentialsException.class, () ->
                userService.login("invalid@example.com", "password"));
    }

    @Test
    public void testLoginInvalidPassword() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Invalid email or password"));
        assertThrows(BadCredentialsException.class, () ->
                userService.login(testUser.getEmail(), "invalidPassword"));
    }
}
