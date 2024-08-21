package flashcards.controllers;

import flashcards.requests.userRequests.LoginRequest;
import flashcards.requests.userRequests.RegisterRequest;
import flashcards.responses.TokenResponse;
import flashcards.responses.UserRegisterResponse;
import flashcards.services.interfaces.CollectionService;
import flashcards.services.interfaces.UserService;
import jakarta.validation.Valid;
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
    private final CollectionService collectionService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){

        UserRegisterResponse response = userService.createUser(request.getUsername(),
                request.getEmail(), request.getPassword());
        //collectionService.createDefaultCollection(response.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){

        TokenResponse token = userService.login(request.getEmail(),
                request.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
