package flashcards.requests.collectionRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCollectionRequest {
    
    @NotBlank(message = "The tittle cannot be blank")
    private String title;
    
    //@NotBlank(message = "The description cannot be blank")
    private String description;
    
    //for postman -> "public": true/false
    private boolean isPublic;
}
