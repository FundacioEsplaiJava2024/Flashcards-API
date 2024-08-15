package flashcards.requests.userRequests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
