package flashcards.requests.collectionRequests;

import lombok.Data;

@Data
public class UpdateCollectionRequest {
    private String title;
    private String description;
    //for postman -> "public": true/false
    private boolean isPublic;
}
