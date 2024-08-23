package flashcards.services;

import flashcards.entities.User;
import flashcards.exceptions.customexceptions.CardNotFoundException;
import flashcards.exceptions.customexceptions.UserAlreadyExistsException;
import flashcards.repos.interfaces.UserRepository;
import flashcards.security.JwtService;
import flashcards.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(String username, String email, String password) {
        User userUsername = userRepository.findByUsername(username);
        if(userUsername != null){
            throw new UserAlreadyExistsException("User with username "  + username + " already exists");
        }

        User userEmail = userRepository.findByEmail(email);
        if(userEmail != null){
            throw new UserAlreadyExistsException("User with email "  + email + " already exists");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .registerDate(LocalDateTime.now())
                .enabled(false)
                .build();

        Integer user_id = userRepository.addUser(user);
        user.setId(user_id);
        return user;

    }

    @Override
    public String login(String email, String password) {
            User user = userRepository.findByEmail(email);

            try {
                if(user == null){
                    throw new BadCredentialsException("Invalid email or password, " +
                            "make sure you are registered and your account was verified");
                }
                var upAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), password);
                Authentication auth = authenticationManager.authenticate(upAuth);
                var jwtToken = jwtService.generateToken((User) auth.getPrincipal());

                return jwtToken;
            } catch (AuthenticationException e) {
                throw new BadCredentialsException("Invalid email or password, " +
                        "make sure you are registered and your account was verified");
            }

    }


    @Override
    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userRepository.findByUsername(username);
        return loggedUser;
    }

    @Override
    public User getUserByUsername(String username){
        User user =  userRepository.findByUsername(username);
        return user;
    }
}
