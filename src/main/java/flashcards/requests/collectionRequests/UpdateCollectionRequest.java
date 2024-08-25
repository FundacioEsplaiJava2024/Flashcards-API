package flashcards.requests.collectionRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCollectionRequest {

    @NotBlank(message = "The tittle cannot be blank")
    private String title;
    private String description;
    private boolean isPublic;
}
