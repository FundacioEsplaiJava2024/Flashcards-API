package flashcards.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CardCollectionResponse {
    private Integer id;
    private String title;
    private String description;
    private boolean publicCollection;
    private LocalDateTime createdAt;
    private String username;
}
