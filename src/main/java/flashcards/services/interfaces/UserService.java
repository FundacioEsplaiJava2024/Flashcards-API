package flashcards.services.interfaces;

import flashcards.entities.User;
import flashcards.responses.TokenResponse;
import flashcards.responses.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse createUser(String username, String email, String password);

    TokenResponse login(String email, String password);

    User getLoggedUser();
}
