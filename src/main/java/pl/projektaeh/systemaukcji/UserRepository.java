package pl.projektaeh.systemaukcji;

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
                "id_user = ?", BeanPropertyRowMapper.newInstance(User.class), id);
    }

//    public int save(List<User> users) {
//        users.forEach(user -> jdbcTemplate
//                .update("INSERT INTO movie(name, rating) VALUES(?, ?)",
//                        user.getName(), user.getRating()
//                ));
//
//        return 1;
//    }
//
//    public int update(User user) {
//        return jdbcTemplate.update("UPDATE movie SET name=?, rating=? WHERE id=?",
//                user.getName(), user.getRating(), user.getId());
//    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM movie WHERE id=?", id);
    }
}

