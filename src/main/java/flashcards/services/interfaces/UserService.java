package flashcards.services.interfaces;

import flashcards.entities.User;
import flashcards.responses.TokenResponse;
import flashcards.responses.UserRegisterResponse;

public interface UserService {
    User createUser(String username, String email, String password);

    String login(String email, String password);

    User getLoggedUser();

    User getUserByUsername(String username);
}
