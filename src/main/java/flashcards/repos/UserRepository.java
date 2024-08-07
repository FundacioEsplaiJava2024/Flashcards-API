package flashcards.repos;

import flashcards.enteties.User;

import java.util.Optional;

public interface UserRepository {
        User findByUsername(String username);
        int addUser(User user);
        Optional<User> findById(Integer id);

}
