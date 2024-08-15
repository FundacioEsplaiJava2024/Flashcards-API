package flashcards.requests.card;

import lombok.Data;

@Data
public class CreateCardRequest {
    private String front;
    private String backside;
    private Integer collection_id;
}
