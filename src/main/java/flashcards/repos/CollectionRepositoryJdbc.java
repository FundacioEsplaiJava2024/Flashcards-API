package flashcards.repos;

import flashcards.enteties.Collection;
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
                "(title, description, created_at, is_public,  user_id) values (?,?,?,?,?)";
        return jdbcTemplate.update(insertQuery,collection.getTitle()
                ,collection.getDescription(),collection.getCreatedAt(), collection.isPublic(), collection.getUser().getId());
    }

    @Override
    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM collections WHERE id=?", id);
    }

    @Override
    public Optional<Collection> findById(Integer id) {
        String selectQuery = "select * from collections where id=?";
        return Optional.of(jdbcTemplate.queryForObject(selectQuery, BeanPropertyRowMapper.newInstance(Collection.class), id));

    }

    @Override
    public int updateCollection(Collection collection) {

        String updateQuery= "UPDATE collections SET id=?, title=?, description=?, created_at=?, is_public=? user_id=?  WHERE id=?";

        return jdbcTemplate.update(updateQuery,
                collection.getTitle(),collection.getDescription(), collection.getCreatedAt(), collection.isPublic(), collection.getUser().getId(), collection.getId());
    }


    @Override
    public List<Collection> findAll(Integer user_id) {
        String selectQuery = "SELECT * from collections WHERE user_id=?";
        return jdbcTemplate.query(selectQuery, BeanPropertyRowMapper.newInstance(Collection.class), user_id );

    }
}
