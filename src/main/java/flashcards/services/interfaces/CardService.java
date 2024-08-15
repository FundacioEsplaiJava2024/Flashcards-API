package flashcards.services.interfaces;

import flashcards.entities.Card;

import java.util.List;

public interface CardService {
    Card addCard(String frontside, String backside, Integer collection_id);

    Card getCardById(Integer id);

    void deleteCard(Integer id);

    Card updateCard(String frontside, String backside, Integer id);

    List<Card> getAllCards();

    List<Card> getAllCardsByCollection(Integer collection_id);
}
