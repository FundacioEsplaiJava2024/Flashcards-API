package flashcards.repos.interfaces;

import flashcards.enteties.Collection;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface CollectionRepository {
    int addCollection(Collection collection);

    int deleteById(Integer id);

    Optional<Collection> findById(Integer id);

    int updateCollection(Collection collection);

    List<Collection> findAll(Integer user_id);
}
