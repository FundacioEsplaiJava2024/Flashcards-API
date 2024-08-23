package flashcards.services.interfaces;

import flashcards.entities.Card;
import flashcards.responses.CardResponse;

import java.util.List;

public interface CardService {
    Card addCard(String frontside, String backside, Integer collection_id, List<String> hashtags);

    Card getCardById(Integer id);

    String deleteCard(Integer id);

    Card updateCard(String frontside, String backside, Integer id);

    List<Card> getAllCards();

    List<Card> getAllCardsByCollection(Integer collection_id);

    //change favourite
    Card changeFavourite(Integer id);

    // random cards
    List<Card> getRandomCards();

    List<Card> getAllFavourite();

    List<Card> getCardsByHashtag(String hashtag);

    Card addHashtag(List<String> hashtag, Integer card_id);

    String deleteHashtag(Integer card_id, String hashtag);
}
