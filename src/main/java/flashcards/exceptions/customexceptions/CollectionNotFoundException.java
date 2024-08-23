package flashcards.exceptions.customexceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CollectionNotFoundException extends RuntimeException {
    public CollectionNotFoundException(String message) {
        super(message);
    }
}