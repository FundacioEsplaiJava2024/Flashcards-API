package flashcards.repos;

import flashcards.repos.interfaces.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class HashtagRepositoryJdbc implements HashtagRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int addHashtag(Integer card_id, String hashtag) {
        String insertQuery = "INSERT INTO hashtags" +
                "(hashtag, card_id) VALUES (?,?)";
        return jdbcTemplate.update(insertQuery, hashtag, card_id);
    }

    @Override
    public List<Integer> findAll(String hashtag) {
        String selectQuery = "SELECT card_id FROM hashtags WHERE hashtag=?";
        return jdbcTemplate.queryForList(selectQuery, Integer.class , hashtag );
    }

    @Override
    public int deleteHashtag(Integer card_id, String hashtag) {
        String deleteQuery = "DELETE FROM hashtags WHERE card_id=? AND hashtag=?";
        return jdbcTemplate.update(deleteQuery, card_id, hashtag);
    }

    @Override
    public List<String> findAllHashtags(Integer card_id) {
        String selectQuery = "SELECT hashtag FROM hashtags WHERE card_id=?";
        return jdbcTemplate.queryForList(selectQuery, String.class , card_id );
    }

}
