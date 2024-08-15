package flashcards.repos;

import flashcards.entities.Collection;
import flashcards.entities.User;
import flashcards.repos.interfaces.CollectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CollectionRepositoryJdbc implements CollectionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int addCollection(Collection collection) {
        String insertQuery = "insert into collections" +
                "(title, description, created_at, is_public, user_id) values (?,?,?,?,?)";
        return jdbcTemplate.update(insertQuery,collection.getTitle()
                ,collection.getDescription(),collection.getCreatedAt(), collection.isPublic(), collection.getUser().getId());
    }

    @Override
    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM collections WHERE id=?", id);
    }

    @Override
    public Optional<Collection> findById(Integer id) {
            String query = "SELECT c.id AS collection_id, c.title, c.description, c.created_at, c.is_public, " +
                    "u.id AS user_id, u.username, u.email " +
                    "FROM collections c " +
                    "JOIN users u ON c.user_id = u.id " +
                    "WHERE c.id = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {

                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));


                Collection collection = new Collection();
                collection.setId(rs.getInt("collection_id"));
                collection.setTitle(rs.getString("title"));
                collection.setDescription(rs.getString("description"));
                collection.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                collection.setPublic(rs.getBoolean("is_public"));
                collection.setUser(user);
                return collection;
            }));
        }



    @Override
    public Optional<Collection> updateCollection(Collection collection, Integer id) {
        String updateQuery = "UPDATE collections SET title=?, description=?, is_public=? WHERE id=?";


        jdbcTemplate.update(updateQuery,
                collection.getTitle(),
                collection.getDescription(),
                collection.isPublic(),
                id);
        return findById(id);
    }


    @Override
    public List<Collection> findAll(Integer user_id) {
        String selectQuery = "SELECT * from collections WHERE user_id=?";
        return jdbcTemplate.query(selectQuery, BeanPropertyRowMapper.newInstance(Collection.class), user_id );

    }
}
