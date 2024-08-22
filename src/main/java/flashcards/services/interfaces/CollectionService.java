package flashcards.services.interfaces;

import flashcards.entities.CardCollection;
import flashcards.responses.CardCollectionResponse;

import java.util.List;

public interface CollectionService {

    CardCollectionResponse createCollection(String title, String description, boolean isPublic);

    // creating a 'default' cardCollection for all those cards that doesn't have any specified cardCollection
    void createDefaultCollection(String username);

    void deleteById(Integer id);

    CardCollectionResponse updateCollection(Integer id, String title, String description);

    CardCollectionResponse getCollectionById(Integer id);

    List<CardCollectionResponse> getLoggedUserAllCollections();
    CardCollection getCollectionByIdForCards(Integer id);

    CardCollectionResponse changePublicStatus(Integer id, boolean isPublic);

    List<CardCollectionResponse> getRandomCollections();

    CardCollectionResponse saveOtherCollection(Integer collection_id);
}
