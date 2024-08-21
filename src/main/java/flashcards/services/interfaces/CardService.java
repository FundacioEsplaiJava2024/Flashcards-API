package flashcards.services.interfaces;

import flashcards.entities.Card;
import flashcards.responses.CardResponse;

import java.util.List;

public interface CardService {
    CardResponse addCard(String frontside, String backside, Integer collection_id, List<String> hashtags);

    CardResponse getCardById(Integer id);

    void deleteCard(Integer id);

    CardResponse updateCard(String frontside, String backside, Integer id);

    List<CardResponse> getAllCards();

    List<CardResponse> getAllCardsByCollection(Integer collection_id);

    //change favourite
    CardResponse changeFavourite(Integer id);

    // random cards
    List<CardResponse> getRandomCards();

    List<CardResponse> getAllFavourite();
}
