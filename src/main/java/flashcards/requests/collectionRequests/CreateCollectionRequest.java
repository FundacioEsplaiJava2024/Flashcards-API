package flashcards.requests.collectionRequests;

import lombok.Data;

@Data
public class CreateCollectionRequest {
    private String title;
    private String description;
    private boolean isPublic;
}
