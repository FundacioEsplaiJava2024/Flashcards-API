package flashcards.repos.interfaces;

import flashcards.entities.User;

import java.util.Optional;

public interface UserRepository {
        User findByUsername(String username);
        int addUser(User user);
        Optional<User> findById(Integer id);
        Optional<User> findByEmail(String email);

}
