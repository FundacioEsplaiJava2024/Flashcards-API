package flashcards.services.interfaces;

import flashcards.entities.CardCollection;

import java.util.List;

public interface CollectionService {
    CardCollection createCollection(String title, String description, boolean isPublic);

    // creating a 'default' cardCollection for all those cards that doesn't have any specified cardCollection
    void createDefaultCollection();

    void deleteById(Integer id);

    CardCollection updateCollection(Integer id, String title, String description, boolean isPublic);

    CardCollection getCollectionById(Integer id);

    List<CardCollection> getAllCollections();
}
