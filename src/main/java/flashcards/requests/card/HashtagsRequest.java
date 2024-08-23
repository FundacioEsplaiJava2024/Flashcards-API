package flashcards.requests.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class HashtagsRequest {
    private List<String> hashtags;

    @JsonCreator
    public HashtagsRequest(@JsonProperty("hashtags") List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
