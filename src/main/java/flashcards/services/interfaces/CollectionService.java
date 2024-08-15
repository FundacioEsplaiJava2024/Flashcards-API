package flashcards.services.interfaces;

import flashcards.entities.Collection;

import java.util.List;

public interface CollectionService {
    Collection createCollection(String title, String description, boolean isPublic);

    // creating a 'default' collection for all those cards that doesn't have any specified collection
    void createDefaultCollection();

    void deleteById(Integer id);

    Collection updateCollection(Integer id, String title, String description, boolean isPublic);

    Collection getCollectionById(Integer id);

    List<Collection> getAllCollections();
}
