package flashcards.mapper;

import flashcards.entities.User;
import flashcards.responses.UserRegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserRegisterResponse toResponse(User user) {
        return new UserRegisterResponse(user.getUsername(), user.getEmail(), user.getRegisterDate());
    }

}
