package flashcards.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CardResponse {
    private Integer id;
    private String front;
    private String back;
    private boolean favourite;
    private LocalDateTime createdAt;
    private List<String> hashtags;
    private String collection_title;
    private String username;
}
