package flashcards.repos.interfaces;

import java.util.List;

public interface HashtagRepository {
    int addHashtag(Integer card_id, String hashtag);

    List<Integer> findAll(String hashtag);

    int deleteHashtag(Integer card_id, String hashtag);

    List<String> findAllHashtags(Integer card_id);
}
