package flashcards.mapper;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.responses.CardCollectionResponse;
import flashcards.responses.CardResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CardMapper {

    public static CardResponse toResponse(Card card) {
        return new CardResponse(card.getId(), card.getFront(), card.getBackside(), card.isFavourite()
                , card.getCreatedAt(), card.getHashtags(), card.getCardCollection().getTitle(), card.getUser().getUsername() );
    }

    public  static List<CardResponse> toResponseList(List<Card> cards) {
        return cards.stream()
                .map(CardMapper::toResponse)
                .collect(Collectors.toList());
    }
}
