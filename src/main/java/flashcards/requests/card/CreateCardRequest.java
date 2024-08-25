package flashcards.requests.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateCardRequest {

    @NotNull(message = "The front of the card cannot be empty")
    @Size(max = 255, message = "The front of the card cannot exceed 255 characters")
    private String front;

    @Size(max = 255, message = "The backside of the card cannot exceed 255 characters")
    private String backside;

    @NotNull(message = "The collection ID is required")
    private Integer collection_id;

    private List<String> hashtags;

}
