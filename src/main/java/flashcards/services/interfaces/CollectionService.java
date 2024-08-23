package flashcards.services.interfaces;

import flashcards.entities.CardCollection;
import flashcards.responses.CardCollectionResponse;

import java.util.List;

public interface CollectionService {

    CardCollection createCollection(String title, String description, boolean isPublic);

    // creating a 'default' cardCollection for all those cards that doesn't have any specified cardCollection
    void createDefaultCollection(String username);

    void deleteById(Integer id);

    CardCollection updateCollection(Integer id, String title, String description);

    CardCollection getCollectionById(Integer id);

    List<CardCollection> getLoggedUserAllCollections();
    CardCollection getCollectionByIdForCards(Integer id);

    CardCollection changePublicStatus(Integer id, boolean isPublic);

    List<CardCollection> getRandomCollections();

    CardCollection saveOtherCollection(Integer collection_id);

    List<CardCollection> findCollectionByTitle(String title);

    List<CardCollection> getOtherSavedCollections();

    void deleteOtherSavedCollection(Integer collection_id);
}
