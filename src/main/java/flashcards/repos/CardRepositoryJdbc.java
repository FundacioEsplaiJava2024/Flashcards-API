package flashcards.repos;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.repos.interfaces.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor

public class CardRepositoryJdbc implements CardRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int addCard(Card card) {
        String insertQuery = "INSERT INTO cards" +
                "(front, backside, created_at, is_favourite, collection_id, user_id) VALUES (?,?,?,?,?,?)";
        return jdbcTemplate.update(insertQuery, card.getFront(), card.getBackside(), card.getCreatedAt(),
                            card.isFavourite(), card.getCardCollection().getId(), card.getUser().getId());
    }

    @Override
    public int deleteById(Integer id) {
        String deleteQuery = "DELETE FROM cards WHERE id=?";
        return jdbcTemplate.update(deleteQuery, id);
    }


    @Override
    public Optional<Card> findById(Integer card_id) {
        String query = "SELECT c.id AS card_id, c.front, c.backside, c.created_at, c.is_favourite, \n" +
                "                col.id AS collection_id, col.title AS collection_title,\n" +
                "                u.id AS user_id, u.username\n" +
                "                FROM cards c \n" +
                "                JOIN collections col ON c.collection_id = col.id \n" +
                "                JOIN users u ON c.user_id = u.id \n" +
                "                WHERE c.id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{card_id}, (rs, rowNum) -> {

            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));



            CardCollection cardCollection = new CardCollection();
            cardCollection.setId(rs.getInt("collection_id"));
            cardCollection.setTitle(rs.getString("collection_title"));

            Card card = new Card();
            card.setId(rs.getInt("card_id"));
            card.setFront(rs.getString("front"));
            card.setBackside(rs.getString("backside"));
            card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            card.setFavourite(rs.getBoolean("is_favourite"));
            card.setCardCollection(cardCollection);
            card.setUser(user);

            return card;
        }));
    }



    @Override
    public Optional<Card> updateCard(Card card, Integer id) {
        String updateQuery = "UPDATE cards SET front=?, backside=? WHERE id=?";
        jdbcTemplate.update(updateQuery,
                card.getFront(),
                card.getBackside(),
                id);
        return findById(id);
    }


    @Override
    public List<Card> findAll(Integer user_id) {
        String selectQuery = "SELECT * FROM cards WHERE user_id=?";
        return jdbcTemplate.query(selectQuery, BeanPropertyRowMapper.newInstance(Card.class), user_id );
    }

    @Override
    public List<Card> findAllByCollection(Integer collection_id) {
        String selectQuery = "SELECT * FROM cards WHERE collection_id=?";
        return jdbcTemplate.query(selectQuery, BeanPropertyRowMapper.newInstance(Card.class), collection_id );
    }
}
