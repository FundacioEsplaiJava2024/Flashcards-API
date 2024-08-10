package flashcards.services.interfaces;

import flashcards.entities.User;

public interface UserService {
    User createUser(String username, String email, String password);

    String login(String email, String password);

    User getLoggedUser();
}
