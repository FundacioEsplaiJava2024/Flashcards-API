package flashcards.repos;

import flashcards.entities.User;
import flashcards.repos.interfaces.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryJdbc implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public User findByUsername(String username) {
        String selectByUsernameQuery = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(
                    selectByUsernameQuery,
                    BeanPropertyRowMapper.newInstance(User.class),
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int addUser(User user) {
        String insertQuery = "insert into users" +
                "(username, email, password, register_date, is_enabled) values (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(user.getRegisterDate()));
            ps.setBoolean(5, user.isEnabled());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Optional<User> findById(Integer id) {

        return Optional.ofNullable(jdbcTemplate.queryForObject
                ("select * from users where id=?", new Object[]{id},
                        new BeanPropertyRowMapper<User>(User.class)));
    }

    @Override
    public User findByEmail(String email) {
        String selectByUsernameQuery = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(
                    selectByUsernameQuery,
                    BeanPropertyRowMapper.newInstance(User.class),
                    email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
