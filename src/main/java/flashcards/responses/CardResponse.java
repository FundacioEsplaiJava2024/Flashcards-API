package flashcards.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CardResponse {
    private String front;
    private String back;
    private boolean favourite;
    private LocalDateTime createdAt;
    private String collection_title;
    private String username;
}
