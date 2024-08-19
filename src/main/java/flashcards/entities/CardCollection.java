package flashcards.entities;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardCollection {

    private Integer id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    //for postman -> "public": true/false
    private boolean isPublic;

    @JsonProperty("user_id")
    private User user;

    @JsonIgnore
    @JsonIdentityReference(alwaysAsId = true)
    private List<Card> cards;


}
