package flashcards.repos;

import flashcards.entities.CardCollection;
import flashcards.entities.User;
import flashcards.exceptions.CollectionNotFoundException;
import flashcards.repos.interfaces.CollectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CollectionRepositoryJdbc implements CollectionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int addCollection(CardCollection cardCollection) {
        String insertQuery = "INSERT INTO collections" +
                "(title, description, created_at, is_public, user_id) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(insertQuery, cardCollection.getTitle()
                , cardCollection.getDescription(), cardCollection.getCreatedAt(), cardCollection.isPublic(), cardCollection.getUser().getId());
    }

    @Override
    public int deleteById(Integer id) {
        String deleteQuery = "DELETE FROM collections WHERE id=?";
        return jdbcTemplate.update(deleteQuery, id);
    }

    @Override
    public CardCollection changePublicStatus(Integer id, CardCollection cardCollection) {
        String updateQuery = "UPDATE collections SET is_public=? WHERE id=?";

        jdbcTemplate.update(updateQuery,
                cardCollection.isPublic(),
                id);
        return findById(id).get();
    }

    @Override
    public Optional<CardCollection> findById(Integer id) {
            String query = "SELECT c.id AS collection_id, c.title, c.description, c.created_at, c.is_public, " +
                    "u.id AS user_id, u.username, u.email " +
                    "FROM collections c " +
                    "JOIN users u ON c.user_id = u.id " +
                    "WHERE c.id = ?";

        try {
            CardCollection cardCollection = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {

                User user = User.builder()
                        .id(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .build();

                return CardCollection.builder()
                        .id(rs.getInt("collection_id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .isPublic(rs.getBoolean("is_public"))
                        .user(user)
                        .build();
            });

            return Optional.ofNullable(cardCollection);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        }



    @Override
    public Optional<CardCollection> updateCollection(CardCollection cardCollection, Integer id) {
        String updateQuery = "UPDATE collections SET title=?, description=? WHERE id=?";


        jdbcTemplate.update(updateQuery,
                cardCollection.getTitle(),
                cardCollection.getDescription(),
                id);
        return findById(id);
    }


    @Override
    public List<CardCollection> findAll(Integer user_id) {
        String query = "SELECT col.id AS collection_id, col.title, col.description, col.created_at, col.is_public, col.user_id, " +
                "u.id AS user_id, u.username " +
                "FROM collections col " +
                "JOIN users u ON col.user_id = u.id " +
                "WHERE col.user_id = ?";

        return jdbcTemplate.query(query, new Object[]{user_id}, (rs, rowNum) -> {

            User user = User.builder()
                    .id(rs.getInt("user_id"))
                    .username(rs.getString("username"))
                    .build();


            CardCollection cardCollection = CardCollection.builder()
                    .id(rs.getInt("collection_id"))
                    .title(rs.getString("title"))  // corrected to use alias directly
                    .description(rs.getString("description")) // corrected to use alias directly
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .isPublic(rs.getBoolean("is_public"))
                    .user(user)
                    .build();

            return cardCollection;
        });
    }

    @Override
    public List<CardCollection> getRandomCollections(Integer user_id) {
        String query = "SELECT col.id AS collection_id, col.title, col.description, " +
                "u.username " +
                "FROM collections col " +
                "JOIN users u ON col.user_id = u.id " +
                "WHERE col.is_public = TRUE AND col.user_id != ? " +
                "ORDER BY RAND() LIMIT 20";

        return jdbcTemplate.query(query, new Object[]{user_id}, (rs, rowNum) -> {

            User user = User.builder()
                    .username(rs.getString("username"))
                    .build();

            CardCollection cardCollection = CardCollection.builder()
                    .id(rs.getInt("collection_id"))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .user(user)
                    .build();

            return cardCollection;
        });
    }

    @Override
    public int saveOtherCollection(Integer collection_id, Integer user_id){
        String insertQuery = "INSERT INTO user_collections" +
                "(collection_id, user_id) VALUES (?,?)";
        return jdbcTemplate.update(insertQuery, collection_id, user_id);
    }
}

