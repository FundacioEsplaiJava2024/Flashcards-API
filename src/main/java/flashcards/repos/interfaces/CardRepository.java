package flashcards.repos.interfaces;

import flashcards.entities.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    int addCard(Card card);

    int deleteById(Integer id);

    Optional<Card> findById(Integer id);

    Optional<Card> updateCard(Card card, Integer id);

    List<Card> findAll(Integer user_id);

    List<Card> findAllByCollection(Integer collection_id);

    Optional<Card> changeFavourite(boolean is_favourite, Integer id);
    List<Card> getRandomCards();

    List<Card> findAllFavourite(Integer user_id);
}
