package flashcards.repos;

import flashcards.entities.Card;
import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.repos.interfaces.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor

public class CardRepositoryJdbc implements CardRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int addCard(Card card) {
        String insertQuery = "INSERT INTO cards" +
                "(front, backside, created_at, is_favourite, collection_id, user_id) VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[] {"id"});
            ps.setString(1, card.getFront());
            ps.setString(2, card.getBackside());
            ps.setTimestamp(3, Timestamp.valueOf(card.getCreatedAt()));
            ps.setBoolean(4, card.isFavourite());
            ps.setInt(5, card.getCardCollection().getId());
            ps.setInt(6, card.getUser().getId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public int deleteById(Integer id) {
        String deleteQuery = "DELETE FROM cards WHERE id=?";
        return jdbcTemplate.update(deleteQuery, id);
    }


    @Override
    public Optional<Card> findById(Integer card_id) {
        String query = "SELECT c.id AS card_id, c.front, c.backside, c.created_at, c.is_favourite, \n" +
                "                col.id AS collection_id, col.title AS collection_title, col.is_public,\n" +
                "                u.id AS user_id, u.username\n" +
                "                FROM cards c \n" +
                "                JOIN collections col ON c.collection_id = col.id \n" +
                "                JOIN users u ON c.user_id = u.id \n" +
                "                WHERE c.id = ?";

        try {

            Card card = jdbcTemplate.queryForObject(query, new Object[]{card_id}, (rs, rowNum) -> {

                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));


                CardCollection cardCollection = new CardCollection();
                cardCollection.setId(rs.getInt("collection_id"));
                cardCollection.setTitle(rs.getString("collection_title"));
                cardCollection.setPublic((rs.getBoolean("col.is_public")));

                return Card.builder()
                        .id(rs.getInt("card_id"))
                        .front(rs.getString("front"))
                        .backside(rs.getString("backside"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .favourite(rs.getBoolean("is_favourite"))
                        .cardCollection(cardCollection)
                        .user(user)
                        .build();


            });
            return Optional.ofNullable(card);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
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
        String selectQuery = "SELECT c.id AS card_id, c.front, c.backside, c.created_at, c.is_favourite, " +
                "col.id AS collection_id, col.title AS collection_title, col.is_public, " +
                "u.id AS user_id, u.username " +
                "FROM cards c " +
                "JOIN collections col ON c.collection_id = col.id " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE col.id = ?";

        return jdbcTemplate.query(selectQuery, new Object[]{collection_id}, (rs, rowNum) -> {

            User user = User.builder()
                    .id(rs.getInt("user_id"))
                    .username(rs.getString("username"))
                    .build();

            CardCollection cardCollection = CardCollection.builder()
                    .id(rs.getInt("collection_id"))
                    .title(rs.getString("collection_title"))
                    .isPublic(rs.getBoolean("is_public"))
                    .build();

            Card card = new Card();
            card.setId(rs.getInt("card_id"));
            card.setFront(rs.getString("front"));
            card.setBackside(rs.getString("backside"));
            card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            card.setFavourite(rs.getBoolean("is_favourite"));
            card.setCardCollection(cardCollection);
            card.setUser(user);

            return card;
        });
    }

    //to mark card as favourite or not
    @Override
    public Optional<Card> changeFavourite(boolean is_favourite, Integer id){
        String updateQuery = "UPDATE cards SET is_favourite=? WHERE id=?";
        jdbcTemplate.update(updateQuery, is_favourite, id);
        return findById(id);

    }



    //random cards where collection is public
    @Override
    public List<Card> getRandomCards() {
        String query = "SELECT c.id AS card_id, c.front, c.backside, c.created_at, c.is_favourite, " +
                "col.id AS collection_id, col.title AS collection_title, col.is_public, " +
                "u.id AS user_id, u.username " +
                "FROM cards c " +
                "JOIN collections col ON c.collection_id = col.id " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE col.is_public = TRUE " +
                "ORDER BY RAND() LIMIT 20";

        return jdbcTemplate.query(query, (rs, rowNum) -> {

            User user = User.builder()
                    .id(rs.getInt("user_id"))
                    .username(rs.getString("username"))
                    .build();

            CardCollection cardCollection = CardCollection.builder()
                    .id(rs.getInt("collection_id"))
                    .title(rs.getString("collection_title"))
                    .build();

            Card card = new Card();
            card.setId(rs.getInt("card_id"));
            card.setFront(rs.getString("front"));
            card.setBackside(rs.getString("backside"));
            card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            card.setFavourite(rs.getBoolean("is_favourite"));
            card.setCardCollection(cardCollection);
            card.setUser(user);

            return card;
        });
    }

    @Override
    public List<Card> findAllFavourite(Integer user_id) {
        String query = "SELECT c.id AS card_id, c.front, c.backside, c.created_at, c.is_favourite, " +
                "col.id AS collection_id, col.title AS collection_title, " +
                "u.id AS user_id, u.username " +
                "FROM cards c " +
                "JOIN collections col ON c.collection_id = col.id " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.is_favourite = TRUE AND c.user_id = ? ";

        return jdbcTemplate.query(query, new Object[]{user_id},  (rs, rowNum) -> {

            User user = User.builder()
                    .id(rs.getInt("user_id"))
                    .username(rs.getString("username"))
                    .build();

            CardCollection cardCollection = CardCollection.builder()
                    .id(rs.getInt("collection_id"))
                    .title(rs.getString("collection_title"))
                    .build();

            Card card = new Card();
            card.setId(rs.getInt("card_id"));
            card.setFront(rs.getString("front"));
            card.setBackside(rs.getString("backside"));
            card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            card.setFavourite(rs.getBoolean("is_favourite"));
            card.setCardCollection(cardCollection);
            card.setUser(user);

            return card;
        });
    }
}
