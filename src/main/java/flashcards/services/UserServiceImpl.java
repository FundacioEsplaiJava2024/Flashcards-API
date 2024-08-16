package flashcards.services;

import flashcards.entities.User;
import flashcards.repos.interfaces.UserRepository;
import flashcards.security.JwtService;
import flashcards.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(String username, String email, String password) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .registerDate(LocalDateTime.now())
                .enabled(false)
                .build();
        userRepository.addUser(user);
        return user;
    }

    @Override
    public String login(String email, String password) {
        String username = getUsername(email);
        var upAuth = new UsernamePasswordAuthenticationToken(username, password);
        var auth = authenticationManager.authenticate(upAuth);

        var jwtToken = jwtService.generateToken((User) auth.getPrincipal());
        return jwtToken;
    }

    private String getUsername(String email){
        User user =  userRepository.findByEmail(email);
        return user.getUsername();
    }

    @Override
    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userRepository.findByUsername(username);
        return loggedUser;
    }
}
