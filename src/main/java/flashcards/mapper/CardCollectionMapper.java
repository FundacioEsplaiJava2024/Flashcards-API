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
        if(collection.getUser() != null) {
            return new CardCollectionResponse(collection.getId(), collection.getTitle(), collection.getDescription()
                    , collection.isPublic(), collection.getCreatedAt(), collection.getUser().getUsername());
        }

            CardCollectionResponse response = CardCollectionResponse.builder()
                    .id(collection.getId())
                    .title(collection.getTitle())
                    .description(collection.getDescription())
                    .createdAt(collection.getCreatedAt())
                    .build();

        return response;


    }

    public  static List<CardCollectionResponse> toResponseList(List<CardCollection> collections) {
        return collections.stream()
                .map(CardCollectionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
