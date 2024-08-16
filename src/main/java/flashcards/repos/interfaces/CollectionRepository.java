package flashcards.repos.interfaces;

import flashcards.entities.CardCollection;

import java.util.List;
import java.util.Optional;


public interface CollectionRepository {
    int addCollection(CardCollection cardCollection);

    int deleteById(Integer id);

    Optional<CardCollection> changePublicStatus(Integer id, CardCollection cardCollection);

    Optional<CardCollection> findById(Integer id);

    Optional<CardCollection> updateCollection(CardCollection cardCollection, Integer id);

    List<CardCollection> findAll(Integer user_id);
}
