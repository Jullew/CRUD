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

/* Controller - klasa, która obsługuje zapytania wysyłane przez przeglądarkę do użytkownika */
public class ProductController {

    private final ProductService productService;

    /* Pobierz wszystkie produkty */
    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    /* Pobierz produkt po id */
    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.getById(id).get();
    }

    /* Dodaj produkt */
    @PostMapping
    public void add(@RequestBody List<Product> products) {
        productService.saveAll(products);
    }

    /* Modyfikuj produkt */
    @PutMapping
    public void update(@RequestBody Product updatedProduct) {
        Optional<Product> product = productService.getById(updatedProduct.getProductId());
        if (product.isPresent()) {
            productService.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    /* Częściowo modyfikuj produkt */
    @PatchMapping
    public void partiallyUpdate(@RequestBody Product updatedProduct) {
        Optional<Product> product = productService.getById(updatedProduct.getProductId());
        if (product.isPresent()) {
            productService.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    /* Usuń produkt */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
