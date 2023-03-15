package pl.aeh_project.auction_system.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users",
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE " +
                "user_id = ?", BeanPropertyRowMapper.newInstance(User.class), id);
    }

    public int save(List<User> users) {
        users.forEach(user -> jdbcTemplate
                .update("INSERT INTO users(login, password, first_name, last_name, api_key, session_key, session_end)" +
                                " VALUES(?, ?, ?, ?, ?, ?, ?)",
                        user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getApiKey(),
                        user.getSessionKey(), user.getSessionEnd()
                ));

        return 1;
    }

    public int update(User user) {
        return jdbcTemplate.update("UPDATE user SET login=?, password=?, first_name=?, last_name=?, api_key=?" +
                        "sessino_key=?, session_end=? WHERE id=?",
                user.getLogin(), user.getPassword(), user.getFirstName(), user.getLastName(),user.getApiKey(),
                user.getSessionKey(), user.getSessionEnd());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM movie WHERE id=?", id);
    }
}

