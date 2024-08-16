package flashcards.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CardCollectionResponse {
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private String username;
}
