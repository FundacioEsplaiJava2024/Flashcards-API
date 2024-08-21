package flashcards.requests.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCardRequest {

    @NotBlank(message = "The front of the card cannot be blank")
    @Size(max = 255, message = "The front of the card cannot exceed 255 characters")
    private String front;

    @NotBlank(message = "The backside of the card cannot be blank")
    @Size(max = 255, message = "The backside of the card cannot exceed 255 characters")
    private String backside;
}
