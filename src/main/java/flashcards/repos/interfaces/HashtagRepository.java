package flashcards.repos.interfaces;

import java.util.List;

public interface HashtagRepository {
    int addHashtag(Integer card_id, String hashtag);

    int deleteById(String hashtag);

    List<Integer> findAll(String hashtag);
}
