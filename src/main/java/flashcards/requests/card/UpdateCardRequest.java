package flashcards.requests.card;

import lombok.Data;

@Data
public class UpdateCardRequest {
    private String front;
    private String backside;
}
