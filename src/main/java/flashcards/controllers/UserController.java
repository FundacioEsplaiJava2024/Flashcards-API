package flashcards.controllers;

import flashcards.requests.userRequests.LoginRequest;
import flashcards.requests.userRequests.RegisterRequest;
import flashcards.responses.TokenResponse;
import flashcards.responses.UserRegisterResponse;
import flashcards.services.interfaces.UserService;
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

        UserRegisterResponse response = userService.createUser(request.getUsername(),
                request.getEmail(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        TokenResponse token = userService.login(request.getEmail(),
                request.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
