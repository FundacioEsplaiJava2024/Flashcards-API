package flashcards.repos.interfaces;

import flashcards.entities.Collection;

import java.util.List;
import java.util.Optional;


public interface CollectionRepository {
    int addCollection(Collection collection);

    int deleteById(Integer id);

    Optional<Collection> findById(Integer id);

    Optional<Collection> updateCollection(Collection collection, Integer id);

    List<Collection> findAll(Integer user_id);
}
