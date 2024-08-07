package flashcards.services;

import flashcards.enteties.User;

public interface UserService {
    User createUser(String username, String email, String password);

    String login(String email, String password);
}
