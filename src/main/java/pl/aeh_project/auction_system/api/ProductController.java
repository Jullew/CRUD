package pl.aeh_project.auction_system.api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import pl.aeh_project.auction_system.domain.entity.Product;
import pl.aeh_project.auction_system.logic.ProductService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.getById(id).get();
    }

    @PostMapping
    public void add(@RequestBody List<Product> products) {
        productService.saveAll(products);
    }

    @PutMapping
    public void update(@RequestBody Product updatedProduct) {
        Optional<Product> product = productService.getById(updatedProduct.getProductId());
        if (product.isPresent()) {
            productService.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @PatchMapping
    public void partiallyUpdate(@RequestBody Product updatedProduct) {
        Optional<Product> product = productService.getById(updatedProduct.getProductId());
        if (product.isPresent()) {
            productService.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
