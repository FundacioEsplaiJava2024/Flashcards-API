package flashcards.repos;

import flashcards.repos.interfaces.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
    public int deleteById(String hashtag) {
        String deleteQuery = "DELETE FROM hashtags WHERE hashtag=?";
        return jdbcTemplate.update(deleteQuery, hashtag);
    }

    @Override
    public List<Integer> findAll(String hashtag) {
        String selectQuery = "SELECT * FROM hashtags WHERE hashtag=?";
        return jdbcTemplate.query(selectQuery, BeanPropertyRowMapper.newInstance(Integer.class), hashtag );
    }
}
