package pl.aeh_project.auction_system.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("")
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") int id) {
        return productRepository.getById(id);
    }

    @PostMapping("")
    public int add(@RequestBody List<Product> products) {
        return productRepository.save(products);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody Product updatedProduct) {
        Product product = productRepository.getById(id);

        if (product != null) {
            productRepository.update(updatedProduct);

        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @PatchMapping("/{id}")
    public void partiallyUpdate(@PathVariable("id") int id, @RequestBody Product updatedProduct) {
        Product product = productRepository.getById(id);

        if (product != null) {
            productRepository.update(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return productRepository.delete(id);
    }
}
