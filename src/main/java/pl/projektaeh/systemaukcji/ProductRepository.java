//package pl.projektaeh.systemaukcji;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ProductRepository {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    public List<Product> getAll() {
//        return jdbcTemplate.query("SELECT id, name, rating FROM movie",
//                BeanPropertyRowMapper.newInstance(Product.class));
//    }
//
//    public Product getById(int id) {
//        return jdbcTemplate.queryForObject("SELECT id, name, rating FROM movie WHERE " +
//                "id = ?", BeanPropertyRowMapper.newInstance(Product.class), id);
//    }
//
//    public int save(List<Product> products) {
//        products.forEach(product -> jdbcTemplate
//                .update("INSERT INTO movie(name, rating) VALUES(?, ?)",
//                        product.getName(), product.getRating()
//                ));
//
//        return 1;
//    }
//
//    public int update(Movie movie) {
//        return jdbcTemplate.update("UPDATE movie SET name=?, rating=? WHERE id=?",
//                movie.getName(), movie.getRating(), movie.getId());
//    }
//
//    public int delete(int id) {
//        return jdbcTemplate.update("DELETE FROM movie WHERE id=?", id);
//    }
//}
