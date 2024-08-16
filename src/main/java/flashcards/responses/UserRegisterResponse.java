package flashcards.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserRegisterResponse {
    private String username;
    private String email;
    private LocalDateTime register_date;

}
