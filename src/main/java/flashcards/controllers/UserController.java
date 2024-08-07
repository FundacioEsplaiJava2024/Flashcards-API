package flashcards.controllers;

import flashcards.requests.LoginRequest;
import flashcards.requests.RegisterRequest;
import flashcards.services.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/user")
@CrossOrigin("*")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request.getUsername(),
                    request.getEmail(), request.getPassword()));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        System.out.println("starting login");
        //System.out.println("email: " + request.getEmail());
        System.out.println("password: " + request.getPassword());
        String token = userService.login(request.getUsername(),
                request.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
