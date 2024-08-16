package flashcards.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Card {

    private Integer id;

    private String front;

    private String backside;

    private LocalDateTime createdAt;

    private boolean favourite;

    @JsonProperty("collection_id")
    private CardCollection cardCollection;

    @JsonProperty("user_id")
    private User user;
}
