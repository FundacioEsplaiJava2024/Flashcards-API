package flashcards.mapper;

import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.responses.CardCollectionResponse;
import flashcards.responses.UserRegisterResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardCollectionMapper {

    public static CardCollectionResponse toResponse(CardCollection collection) {
        return new CardCollectionResponse(collection.getTitle(), collection.getDescription()
                , collection.isPublic(), collection.getCreatedAt(), collection.getUser().getUsername());
    }

    public  static List<CardCollectionResponse> toResponseList(List<CardCollection> collections) {
        return collections.stream()
                .map(CardCollectionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
