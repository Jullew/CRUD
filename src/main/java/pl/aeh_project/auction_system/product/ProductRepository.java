package pl.aeh_project.auction_system.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> getAll() {
        return jdbcTemplate.query("SELECT * FROM products",
                BeanPropertyRowMapper.newInstance(Product.class));
    }

    public Product getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE " +
                "product_id = ?", BeanPropertyRowMapper.newInstance(Product.class), id);
    }

    public int save(List<Product> products) {
        products.forEach(product -> jdbcTemplate
                .update("INSERT INTO products(user_id, title, description, price, customer_id, end_date) " +
                                "VALUES(?, ?, ?, ?, ?, ?)",
                        product.getUserId(), product.getTitle(), product.getDescription(), product.getPrice(),
                        product.getCustomerId(), product.getEndDate()
                ));

        return 1;
    }

    public int update(Product product) {
        return jdbcTemplate.update("UPDATE products SET description=?, price=?" +
                        "customer_id=?, end_date=? WHERE product_id=?",
                product.getUserId(), product.getTitle(), product.getDescription(), product.getPrice(),
                product.getCustomerId(), product.getEndDate(), product.getProductId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM product WHERE product_id=?", id);
    }
}
