package flashcards.repos;

import flashcards.enteties.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryJdbc implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public User findByUsername(String username) {
        String selectByUsernameQuery = "select id, username, password, register_date from users where username = ?";
        User user = jdbcTemplate.queryForObject(selectByUsernameQuery, BeanPropertyRowMapper.newInstance(User.class), username);
        return user;
    }

    @Override
    public int addUser(User user) {
        String insertQuery = "insert into users" +
                "(username, email, password, register_date, enabled) values (?,?,?,?,?)";
        return jdbcTemplate.update(insertQuery,user.getUsername(),user.getEmail(), user.getPassword(), user.getRegisterDate(), user.isEnabled());
    }

    @Override
    public Optional<User> findById(Integer id) {

        return Optional.of(jdbcTemplate.queryForObject
                ("select * from users where id=?", new Object[]{id},
                        new BeanPropertyRowMapper<User>(User.class)));
    }
}
