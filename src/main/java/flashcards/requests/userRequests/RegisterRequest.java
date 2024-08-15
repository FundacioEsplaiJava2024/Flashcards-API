package flashcards.requests.userRequests;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    @Email
    private String email;
    private String password;
}
